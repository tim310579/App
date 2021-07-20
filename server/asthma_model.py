import socket, select
import sqlite3
import sys
import re
from datetime import date, timedelta, datetime

import pickle
import pandas as pd
import numpy as np
from sklearn import svm

from selenium import webdriver
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.keys import Keys
import time
from bs4 import BeautifulSoup
import requests

host = '0.0.0.0'
port = 10070

#server socket set up
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((host, port)) 
server_socket.listen(15) #set maximum connections
server_socket.setblocking(0) #set nonblocking
#connect to db
con2db = sqlite3.connect('asthma_model.db') #if not exist, create file hw1.db
c = con2db.cursor()

# c.execute("""CREATE TABLE USERS (
#                 uid INTEGER PRIMARY KEY AUTOINCREMENT,
#                 username TEXT NOT NULL UNIQUE,
#                 ref REAL NOT NULL,
#                 county TEXT NOT NULL
#             )""")

# c.execute("""CREATE TABLE WEATHER (
#                 ID INTEGER PRIMARY KEY AUTOINCREMENT,
#                 Date TEXT NOT NULL,
#                 Location TEXT NOT NULL,
#                 avg_tmp REAL,
#                 min_tmp REAL,
#                 max_tmp REAL,
#                 tmp_diff REAL,
#                 avg_humidity REAL
#             )""")

# c.execute("""CREATE TABLE AIR (
#                 ID INTEGER PRIMARY KEY AUTOINCREMENT,
#                 Date TEXT NOT NULL,
#                 Location TEXT NOT NULL,
#                 PM25 REAL,
#                 PM10 REAL,
#                 SO2 REAL,
#                 NOx REAL,
#                 PSI REAL,
#                 CO REAL,
#                 O3 REAL
#             )""")

# c.execute("""CREATE TABLE BIO (
#                 ID INTEGER PRIMARY KEY AUTOINCREMENT,
#                 username TEXT NOT NULL,
#                 Date TEXT NOT NULL,

#                 night_record REAL,
#                 day_record REAL,

#                 nose_symptom1 REAL,
#                 nose_symptom2 REAL,
#                 nose_symptom3 REAL,
#                 nose_symptom4 REAL,

#                 skin_symptom REAL,
#                 eye_symptom REAL,
#                 day_symptom REAL,

#                 asthma REAL,
#                 a_rhinitis REAL,
#                 a_dermatitis REAL,
#                 a_conjunctivitis REAL,

#                 night_pefr REAL,
#                 day_pefr REAL,
#                 grade REAL,
#                 variant REAL
#             )""")


inputs = [server_socket] #the file descripter to keeo track of 
status = {} #dict structure {skt, '0'/'username'} to record the login status, not login-->"0"; login-->"username"
pre1_day = str(date.today())
pre2_day = str(date.today() - timedelta(days=1))

area = ['北部空品區', '竹苗空品區', '中部空品區', '雲嘉南空品區', '高屏空品區', '宜蘭空品區', '花東空品區', '其他']
c1 = ['基隆市', '臺北市', '新北市', '桃園市']
c2 = ['新竹市', '新竹縣', '苗栗縣']
c3 = ['臺中市', '南投縣', '彰化縣']
c4 = ['雲林縣', '嘉義市', '嘉義縣', '臺南市']
c5 = ['高雄市', '屏東縣']
c6 = ['宜蘭縣']
c7 = ['花蓮縣', '臺東縣']
c8 = ['澎湖縣', '金門縣', '連江縣']
c_all = [c1, c2, c3, c4, c5, c6, c7, c8]
C = {}

htmltext = []
for i in range(0, 8):
    for cc in c_all[i]:
        C[cc] = area[i]  

def isfloat(value):
  try:
    float(value)
    return True
  except ValueError:
    return False

def get_raw_air_data(d, C, c_name):
    day = d.split('-')[0] + '/' +  d.split('-')[1] + '/' + d.split('-')[2]
    driver = webdriver.Chrome('./chromedriver')
    driver.get("https://airtw.epa.gov.tw/CHT/EnvMonitoring/Central/CentralMonitoring.aspx") #前往這個網址

    select = Select(driver.find_element_by_id('ddl_Area'))
    time.sleep(0.5)
    select.select_by_visible_text(C[c_name])
    time.sleep(1)

    select = Select(driver.find_element_by_id('ddl_County'))
    time.sleep(2)
    select.select_by_visible_text(c_name)
    time.sleep(0.5)

    select = Select(driver.find_element_by_id('ddl_Site'))
    time.sleep(0.5)
    select.select_by_index(0)
    time.sleep(0.5)

    select = Select(driver.find_element_by_id('ddl_Time'))
    time.sleep(0.5)
    select.select_by_visible_text(day + " " + str(datetime.now()).split(' ')[1].split(':', 1)[0] + ":00")
    time.sleep(0.5)

    start_search_btn =  driver.find_element_by_xpath("//input[@value='查詢' and @class='btn']")
    time.sleep(0.5)
    start_search_btn.click()
    time.sleep(0.5)
    htmltext.append(driver.page_source)
    time.sleep(0.5)

    driver.close()

def get_air_data(raw, loca, d):
    soup = BeautifulSoup(raw, 'lxml')
    air_data = ["AVPM25", "AVPM10", "SO2", "NO2", "AQI", "AVCO", "AVO3"]
    db_names = {"AVPM25": "PM25", "AVPM10": "PM10", "SO2": "SO2", "NO2": "NOx", "AQI": "PSI", "AVCO": "CO", "AVO3": "O3"}
    
    c.execute('INSERT INTO AIR (Location, Date) VALUES (?, ?)', (loca, d)) 
    for a in air_data:
        data = soup.find(id = a).text
        if isfloat(data) == False:
            if a == 'AVPM25':
                data = 17.2
            elif a == "AVPM10":
                data = 36.0
            elif a == "SO2":
                data = 2.30
            elif a == "NO2":
                data = 12.06
            elif a == "AQI":
                data = 61
            elif a == "AVCO":
                data = 0.38
            elif a == "AVO3":
                data = 31.40
        else:
            data = float(data)
        
        c.execute('UPDATE AIR SET ' + db_names[a] + ' = ? WHERE Location = ? AND Date = ?', (data, loca, d))

check_air_data1 = c.execute('SELECT * FROM AIR WHERE Date = ?',(pre1_day, )).fetchall()
check_air_data2 = c.execute('SELECT * FROM AIR WHERE Date = ?',(pre2_day, )).fetchall()

if not check_air_data1:
    for i in range(0, 8):
        for cc in c_all[i]:
            get_raw_air_data(pre1_day, C, cc)
    l = 0
    for i in range(0, 8):
        for cc in c_all[i]:
            get_air_data(htmltext[l], cc, pre1_day)
            l = l + 1
con2db.commit()

htmltext = []
if not check_air_data2:
    for i in range(0, 8):
        for cc in c_all[i]:
            get_raw_air_data(pre2_day, C, cc)
    l = 0
    for i in range(0, 8):
        for cc in c_all[i]:
            get_air_data(htmltext[l], cc, pre2_day)
            l = l + 1
con2db.commit()

weather_dic = {'基隆市':"46694", '臺北市':"46692", '新北市':"46688", '桃園市':"C0C48", 
               '新竹市':"C0D66", '新竹縣':"46757", '苗栗縣':"C0E75",
               '臺中市':"46749", '彰化縣':"46727", '南投縣':"C0H89",
               '雲林縣':"C0K40", '嘉義市':"C0M73", '嘉義縣':"72M70", '臺南市':"C0X10",
               '高雄市':"46744", '屏東縣':"46759", '宜蘭縣':"46708", '花蓮縣':"46699", '臺東縣':"46766",
               '澎湖縣':"46735", '金門縣':"46711", '連江縣':"46799"}

def get_weather_data(cnty, d):
    driver = webdriver.Chrome('./chromedriver')
    driver.get("https://www.cwb.gov.tw/V8/C/W/OBS_Station.html?ID=" + weather_dic[cnty])

    temp = driver.find_elements_by_xpath("//*[@class='tem-C is-active']")
    time.sleep(1)
    all_t = []
    t_max = 0
    t_min = 100
    for t in temp:
        if t.text != '-':
            all_t.append(float(t.text))

        cur_tmp = float(t.text)
        if cur_tmp > t_max:
            t_max = cur_tmp
        if cur_tmp < t_min:
            t_min = cur_tmp

        avg_t = sum(all_t) / len(all_t)
        diff_t = t_max - t_min 
        
    hum = driver.find_elements_by_xpath("//*[@headers='hum']")
    time.sleep(0.5)
    all_h = []
    for h in hum:
        if h.text != '-':
            all_h.append(float(h.text))
    time.sleep(0.5)
    driver.close()
    avg_h = sum(all_h) / len(all_h)
    c.execute('INSERT INTO WEATHER (Location, Date, avg_tmp, min_tmp, max_tmp, tmp_diff, avg_humidity) VALUES (?, ?, ?, ?, ?, ?, ?)', (cnty, d, avg_t, t_min, t_max, diff_t, avg_h))

weather2_dic = {'基隆市':"466940&stname=%25E5%259F%25BA%25E9%259A%2586&datepicker=",
               '臺北市':"466920&stname=%25E8%2587%25BA%25E5%258C%2597&datepicker=", 
               '新北市':"466880&stname=%25E6%259D%25BF%25E6%25A9%258B&datepicker=", 
               '桃園市':"C0C480&stname=%25E6%25A1%2583%25E5%259C%2592&datepicker=", 
               '新竹市':"C0D660&stname=%25E6%2596%25B0%25E7%25AB%25B9%25E5%25B8%2582%25E6%259D%25B1%25E5%258D%2580&datepicker=", 
               '新竹縣':"467571&stname=%25E6%2596%25B0%25E7%25AB%25B9&datepicker=", 
               '苗栗縣':"C0E420&stname=%25E7%25AB%25B9%25E5%258D%2597&datepicker=",
               '臺中市':"467490&stname=%25E8%2587%25BA%25E4%25B8%25AD&datepicker=", 
               '彰化縣':"C0G650&stname=%25E5%2593%25A1%25E6%259E%2597&datepicker=", 
               '南投縣':"C0H890&stname=%25E5%259F%2594%25E9%2587%258C&datepicker=",
               '雲林縣':"C0K400&stname=%25E6%2596%2597%25E5%2585%25AD&datepicker=", 
               '嘉義市':"C0M730&stname=%25E5%2598%2589%25E7%25BE%25A9%25E5%25B8%2582%25E6%259D%25B1%25E5%258D%2580&datepicker=", 
               '嘉義縣':"C0M640&stname=%25E4%25B8%25AD%25E5%259F%2594&datepicker=", 
               '臺南市':"467410&stname=%25E8%2587%25BA%25E5%258D%2597&datepicker=",
               '高雄市':"467440&stname=%25E9%25AB%2598%25E9%259B%2584&datepicker=", 
               '屏東縣':"467590&stname=%25E6%2581%2586%25E6%2598%25A5&datepicker=", 
               '宜蘭縣':"467080&stname=%25E5%25AE%259C%25E8%2598%25AD&datepicker=", 
               '花蓮縣':"466990&stname=%25E8%258A%25B1%25E8%2593%25AE&datepicker=", 
               '臺東縣':"467660&stname=%25E8%2587%25BA%25E6%259D%25B1&datepicker=",
               '澎湖縣':"467350&stname=%25E6%25BE%258E%25E6%25B9%2596&datepicker=", 
               '金門縣':"467110&stname=%25E9%2587%2591%25E9%2596%2580&datepicker=", 
               '連江縣':"467990&stname=%25E9%25A6%25AC%25E7%25A5%2596&datepicker="}

def get_pre2_weather(cnty, d):
    #
    if cnty == '桃園市' or '新竹市':
        re = requests.get("https://e-service.cwb.gov.tw/HistoryDataQuery/DayDataController.do?command=viewMain&station=" + weather2_dic[cnty] + '2020-01-03')
    else: 
        re = requests.get("https://e-service.cwb.gov.tw/HistoryDataQuery/DayDataController.do?command=viewMain&station=" + weather2_dic[cnty] + d)  
    #
    # re = requests.get("https://e-service.cwb.gov.tw/HistoryDataQuery/DayDataController.do?command=viewMain&station=" + weather2_dic[cnty] + d)
    soup = BeautifulSoup(re.text, 'lxml')
    all_data = soup.find_all('td')
    check_start = 0
    cnt = -1
    temp = []
    humi = []
    max_t = 0
    min_t = 100
    for da in all_data:
        if da.text == '01':
            check_start = 1
        if check_start == 1:
            cnt = cnt + 1
            if isfloat(da.text):
                if (cnt - 3)%17 == 0:
                    temp.append(float(da.text))
                    if float(da.text) > max_t:
                        max_t = float(da.text)
                    if float(da.text) < min_t:
                        min_t = float(da.text)
                if (cnt - 5)%17 == 0:
                    humi.append(float(da.text))
    print(cnty)
    avg_t = sum(temp) / len(temp)
    avg_h = sum(humi) / len(humi)
    diff_t = max_t - min_t
    
    c.execute('INSERT INTO WEATHER (Location, Date, avg_tmp, min_tmp, max_tmp, tmp_diff, avg_humidity) VALUES (?, ?, ?, ?, ?, ? ,?)', (cnty, d, avg_t, min_t, max_t, diff_t, avg_h))

check_weather1_data = c.execute('SELECT * FROM WEATHER WHERE Date = ?',(pre1_day, )).fetchall()
check_weather2_data = c.execute('SELECT * FROM WEATHER WHERE Date = ?',(pre2_day, )).fetchall()
if not check_weather1_data:
    for i in range(0, 8):
        for cc in c_all[i]:
            get_weather_data(cc, pre1_day)
con2db.commit()
if not check_weather2_data:
    for i in range(0, 8):
        for idx, cc in enumerate(c_all[i]):
            get_pre2_weather(cc, pre2_day) 
con2db.commit()

feature_sort = ['pre2_night_record', 'pre1_avg_tmp',  'pre2_day_record', 'pre2_min_tmp',
 'pre1_min_tmp', 'pre1_night_pefr', 'pre1_max_tmp', 'pre1_PM2.5', 'pre1_SO2',
 'pre2_nose_symptom1_0.0', 'pre2_a_rhinitis_2.0', 'pre2_SO2',
 'pre2_day_symptom_1.0', 'pre2_nose_symptom4_0.0', 'pre2_asthma_1.0',
 'pre2_NOx', 'pre2_PSI', 'pre1_nose_symptom1_0.0', 'pre1_grade_1.0',
 'pre1_night_record', 'pre1_nose_symptom2_1.0', 'pre1_nose_symptom4_1.0',
 'pre1_day_symptom_1.0', 'pre2_nose_symptom2_0.0', 'pre1_a_rhinitis_2.0',
 'pre1_day_pefr', 'pre1_CO', 'pre1_skin_symptom_0.0', 'pre1_a_dermatitis_1.0',
 'pre1_PM10', 'pre2_eye_symptom_0.0', 'pre2_a_conjunctivitis_1.0',
 'pre1_eye_symptom_0.0', 'pre1_a_conjunctivitis_1.0',
 'pre2_skin_symptom_0.0', 'pre2_a_dermatitis_1.0', 'pre1_avg_humidity',
 'pre1_tmp_diff', 'pre2_O3', 'pre2_night_pefr', 'pre2_variant', 'pre1_O3',
 'pre2_max_tmp', 'pre2_PM2.5', 'pre2_avg_humidity', 'pre1_day_record',
 'pre1_PSI', 'pre1_variant', 'reference', 'pre2_tmp_diff']

while True:
    #monitor the file descipters in inputs, and set the blocking time to 2sec, if there're requests, it'll be put into readable
    readable, output, err = select.select(inputs, [], [], 2)
    #do corresponding action for different file descripter(socket) in readable 
    for skt in readable:
        if skt is server_socket:
            conn, addr = server_socket.accept()
            conn.setblocking(0) #set conn socket to nonblocking
            # conn.send('%Welcome'.encode('utf-8'))
            inputs.append(conn) #add the newly created conn socket to inputs list, so select.select can keep track of it 
            print('New connection.')
            #print('connected by' + str(addr))
            status[conn] = '0' #add the new conn to dict status, and initialize to '0'
        else: #if it's not the server socket, then it must be the conn socket for recv & send
            msg = skt.recv(1024).decode('utf-8').strip()
            print(msg)
            #process different recv msg(command from users)
            if msg is not "": #not "" means it's not sending nothing
                cmd = msg.split()
                
                if cmd[0] == 'exit':
                    if len(cmd) == 1:
                        print('user exit!')
                        status.pop(skt)
                        inputs.remove(skt)
                        skt.close()
                        continue
                    else:
                        skt.send('Usage: exit\n'.encode('utf-8'))
                elif cmd[0] == 'register':
                    if len(cmd) == 7:
                        username = cmd[1]
                        sex = cmd[2]
                        age = int(cmd[3])
                        hight = int(cmd[4]) 
                        weight = int(cmd[5])
                        county = cmd[6]

                        if age >= 13:
                            if sex == 'm':
                                ref = 3.89*hight - 2.95*age + 43.59
                            else: 
                                ref = 4.10*hight - 1.61*age - 173.55
                        elif age >=6:
                            if sex == 'm': 
                                ref = 9.35*age + 2.03*hight + 0.81*weight - 130.5
                            else:
                                ref = 7.37*age + 1.68*hight + 1.28*weight - 98.87
                        else:
                            print('Age too small.')
                        
                        row = c.execute('SELECT * FROM USERS WHERE username = ?', (username, )).fetchall()
                        if not row:
                            c.execute('INSERT INTO USERS (username, ref, county) VALUES (?, ?, ?)', (username, ref, county))
                            print('register success')
                        else:
                            c.execute('UPDATE USERS SET ref = ? WHERE username = ?', (ref, username))
                            c.execute('UPDATE USERS SET county = ? WHERE username = ?', (county, username))
                            print('Update registration info successfully')                
                    else:
                        print('Cmd parameter error.')

                elif cmd[0] == 'input':
                    if len(cmd) == 16:
                        username = cmd[1]
                        Date = cmd[2]
                        # if int(Date.split('-')[1]) < 10:
                        #     Date = Date.split('-')[0] + '-0' + Date.split('-')[1] + '-' + Date.split('-')[2]
                        # if int(Date.split('-')[2]) < 10:
                        #     Date = Date.split('-')[0] + '-' + Date.split('-')[1] + '-0' + Date.split('-')[2]

                        bio_name = ['night_record', 'day_record',
                                    'nose_symptom1', 'nose_symptom2', 'nose_symptom3', 'nose_symptom4',
                                    'skin_symptom', 'eye_symptom', 'day_symptom',
                                    'asthma', 'a_rhinitis', 'a_dermatitis', 'a_conjunctivitis']
                        
                        check_first = c.execute('SELECT * FROM BIO WHERE username = ? AND Date = ?',(username, Date)).fetchall()
                        if not check_first:
                            c.execute('INSERT INTO BIO (username, Date) VALUES (?, ?)', (username, Date))
                            for i in range(3, 16):
                                c.execute('UPDATE BIO SET ' + bio_name[i - 3] + ' = ? WHERE username = ? AND Date = ?', (cmd[i], username, Date))
                            print('create bio data success')
                        else:# must make sure user filled up all the blank in app before submit bio data 
                            for i in range(3, 16):
                                c.execute('UPDATE BIO SET ' + bio_name[i - 3] + ' = ? WHERE username = ? AND Date = ?', (cmd[i], username, Date))
                                print('update bio data success')
                                
                        ref =  c.execute('SELECT ref FROM USERS WHERE username = ?',(username, )).fetchall()[0][0]
                            
                        c.execute('UPDATE BIO SET night_pefr = ? WHERE username = ? AND Date = ?', (float(cmd[3]) / ref, username, Date))
                        c.execute('UPDATE BIO SET day_pefr = ? WHERE username = ? AND Date = ?', (float(cmd[4]) / ref, username, Date))
                        c.execute('UPDATE BIO SET variant = ? WHERE username = ? AND Date = ?', (abs(float(cmd[3]) - float(cmd[4])), username, Date))
                        def cal_grade(day, night):
                            pefr_var = abs(day - night) / ((day + night) / 2)
                            if pefr_var >= 0.3:
                                return 3
                            elif pefr_var >= 0.2:
                                return 2
                            else:
                                return 1
                        c.execute('UPDATE BIO SET grade = ? WHERE username = ? AND Date = ?', (cal_grade(float(cmd[3]), float(cmd[4])), username, Date))
                            
                    else:
                        print('cmd parameter error.')
                elif cmd[0] == 'predict':
                    username = cmd[1]
                    # Date = cmd[2]
    
                    check_two_day = 0
                    pre1_data = c.execute('SELECT * FROM BIO WHERE username = ? AND Date = ?',(username, pre1_day)).fetchall()
                    pre2_data = c.execute('SELECT * FROM BIO WHERE username = ? AND Date = ?',(username, pre2_day)).fetchall()
                    
                    # print('hi', pre1_data, pre2_data)
                    
                    if pre1_data and pre2_data:
                        if len(pre1_data[0]) == 20 and len(pre2_data[0]) == 20:
                            check_two_day = 1
                    
                    if check_two_day == 1:
                        feature_select = ['reference', 'pre2_night_pefr', 'pre2_variant', 'pre1_day_record', 'pre1_variant',
                                          'pre2_night_record', 'pre2_day_record', 'pre1_night_pefr','pre1_night_record', 'pre1_day_pefr',

                                          'pre2_nose_symptom1_0.0', 'pre2_a_rhinitis_2.0',
                                          'pre2_day_symptom_1.0', 'pre2_nose_symptom4_0.0', 'pre2_asthma_1.0',
                                          'pre1_nose_symptom1_0.0', 'pre1_grade_1.0',
                                          'pre1_nose_symptom2_1.0', 'pre1_nose_symptom4_1.0',
                                          'pre1_day_symptom_1.0', 'pre2_nose_symptom2_0.0', 'pre1_a_rhinitis_2.0',
                                          'pre1_skin_symptom_0.0', 'pre1_a_dermatitis_1.0',
                                          'pre2_eye_symptom_0.0', 'pre2_a_conjunctivitis_1.0',
                                          'pre1_eye_symptom_0.0', 'pre1_a_conjunctivitis_1.0',
                                          'pre2_skin_symptom_0.0', 'pre2_a_dermatitis_1.0']
                        d = {}
                        d['reference'] = [c.execute('SELECT ref FROM USERS WHERE username = ?',(username, )).fetchall()[0][0]]
                        #no need ohe
                        for i in range(1, 10):
                            pre_day = feature_select[i].split('_', 1)[0]
                            fe = feature_select[i].split('_', 1)[1]
                            if pre_day == 'pre_1':
                                d[feature_select[i]] = [c.execute('SELECT ' + fe + ' FROM BIO WHERE username = ? AND Date = ?',(username, pre1_day)).fetchall()[0][0]]
                            else:
                                d[feature_select[i]] = [c.execute('SELECT ' + fe + ' FROM BIO WHERE username = ? AND Date = ?',(username, pre2_day)).fetchall()[0][0]]
                        # need ohe adjust
                        for i in range(10, 30):
                            pre_day = feature_select[i].split('_', 1)[0]
                            fe = feature_select[i].split('_', 1)[1].rsplit('_', 1)[0]
                            ohe = feature_select[i].split('_', 1)[1].rsplit('_', 1)[1]
                            if pre_day == 'pre1':
                                without_ohe = c.execute('SELECT ' + fe + ' FROM BIO WHERE username = ? AND Date = ?',(username, pre1_day)).fetchall()[0][0]
                            else:
                                without_ohe = c.execute('SELECT ' + fe + ' FROM BIO WHERE username = ? AND Date = ?',(username, pre2_day)).fetchall()[0][0]
                            
                            if ohe == '0.0':
                                if int(without_ohe) == 0:
                                    d[feature_select[i]] = [1]
                                else:
                                    d[feature_select[i]] = [0]
                            elif ohe == '1.0':
                                if int(without_ohe) == 1:
                                    d[feature_select[i]] = [1]
                                else:
                                    d[feature_select[i]] = [0]
                            elif ohe == '2.0':
                                if int(without_ohe) == 2:
                                    d[feature_select[i]] = [1]
                                else:
                                    d[feature_select[i]] = [0]
                            elif ohe == '3.0':
                                if int(without_ohe) == 3:
                                    d[feature_select[i]] = [1]
                                else:
                                    d[feature_select[i]] = [0]
                            elif ohe == '4.0':
                                if int(without_ohe) == 4:
                                    d[feature_select[i]] = [1]
                                else:
                                    d[feature_select[i]] = [0]
                        # for data in air
                        loca = c.execute('SELECT county FROM USERS WHERE username = ?',(username, )).fetchall()[0][0]
                        # feature_select = ['pre1_avg_tmp', 'pre2_min_tmp', 'pre1_min_tmp', 'pre1_max_tmp', 'pre2_tmp_diff',
                        #                   'pre1_avg_humidity', 'pre1_tmp_diff', 'pre2_max_tmp','pre2_avg_humidity',
                        air_feature = ['pre1_PM2.5', 'pre1_SO2', 'pre2_SO2', 'pre2_NOx', 'pre2_PSI', 
                                       'pre1_CO','pre1_PM10', 'pre2_O3', 'pre1_O3', 'pre2_PM2.5', 'pre1_PSI']
                        for af in air_feature:
                            pre_day = af.split('_')[0]
                            air_type = af.split('_')[1]
                            if air_type == 'PM2.5':
                                air_type = 'PM25'
                            if pre_day == 'pre1':
                                d[af] = [c.execute('SELECT ' + air_type + ' FROM AIR WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0]]
                            else:
                                d[af] = [c.execute('SELECT ' + air_type + ' FROM AIR WHERE Location = ? AND Date = ?',(loca, pre2_day)).fetchall()[0][0]]
                        
                        # for data in weather
                        weather_feature = ['pre1_avg_tmp', 'pre2_min_tmp', 'pre1_min_tmp', 'pre1_max_tmp', 'pre2_tmp_diff',
                                          'pre1_avg_humidity', 'pre1_tmp_diff', 'pre2_max_tmp','pre2_avg_humidity']
                        for wt in weather_feature:
                            pre_day = wt.split('_', 1)[0]
                            weather_type = wt.split('_', 1)[1]
                            if pre_day == 'pre1':
                                d[wt] = [c.execute('SELECT ' + weather_type + ' FROM WEATHER WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0]]
                            else:
                                d[wt] = [c.execute('SELECT ' + weather_type + ' FROM WEATHER WHERE Location = ? AND Date = ?',(loca, pre2_day)).fetchall()[0][0]]

                        #todo: sort feature
                        X_test = pd.DataFrame(data = d)
                        X_test = X_test[feature_sort].copy() 

                        # todo: input model and output result
                        filename = 'trained_asthma_model.pkl'
                        model = pickle.load(open(filename, 'rb'))
                        result = model.predict(X_test)[0]
                        print(result)
                        db_names = ["PM25", "PM10", "SO2", "NOx", "CO", "O3"]
                        loca = c.execute('SELECT county FROM USERS WHERE username = ?',(username, )).fetchall()[0][0]
                        psi_value = int(c.execute('SELECT PSI FROM AIR WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0])
                        psi_max = 0
                        psi_d = 0
                        for d in db_names:
                            d_value = int(c.execute('SELECT ' + d + ' FROM AIR WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0])
                            if d_value > psi_max:
                                psi_max = d_value
                                psi_d = d
                        if psi_value >= 101:
                            skt.send('{} 今日空污指標不良，{}值：{}較高。'.format(result, psi_d, psi_max).encode('utf-8'))
                        else:
                            skt.send('{} 今日空污指標良好。'.format(result).encode('utf-8'))

                    else:
                        skt.send('請先輸入個人資料、昨日及今日生理資料。 noshow\n'.encode('utf-8'))
                elif cmd[0] == 'get_weather_air':
                    username = cmd[1]
                    if username != 'null':
                        loca = c.execute('SELECT county FROM USERS WHERE username = ?',(username, )).fetchall()[0][0]
                        psi = int(c.execute('SELECT PSI FROM AIR WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0])
                        avg_t = c.execute('SELECT avg_tmp FROM WEATHER WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0]
                        avg_h = c.execute('SELECT avg_humidity FROM WEATHER WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0]
                        if psi >= 300:
                            psi = '有害'
                        elif psi >= 200:
                            psi = '非常不良'
                        elif psi >= 101:
                            psi = '不良'
                        elif psi >= 51:
                            psi = '普通'
                        else:
                            psi ='良好'
                        db_names = ["PM25", "PM10", "SO2", "NOx", "CO", "O3"]
                        db_values = []
                        for d in db_names:
                            db_values.append(int(c.execute('SELECT ' + d + ' FROM AIR WHERE Location = ? AND Date = ?',(loca, pre1_day)).fetchall()[0][0]))
                        skt.send('{} {} {} {} {} {} {} {} {}'.format(round(avg_t, 2), round(avg_h, 2), psi, db_values[0], db_values[1], db_values[2], db_values[3], db_values[4], db_values[5]).encode('utf-8'))
                    else:
                        skt.send('No user data!'.encode('utf-8')) 
                else:
                    skt.send('Unknowm command'.encode('utf-8'))
                    print(msg)
                    print('Unknowm command')
                
                #must commit so that chamges made will be updated to the db
                con2db.commit()  
                # skt.send('% '.encode('utf-8'))
                # print('% ') 
        
    
            

            
                
            
    
        
   
        
    


