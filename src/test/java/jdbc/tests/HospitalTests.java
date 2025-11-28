package jdbc.tests;

import io.qameta.allure.Description;
import jdbc.databases.DatabaseConnection;
import jdbc.utils.DatabaseUtils;
import jdbc.utils.SystemUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalTests {
    private static Connection connection;

    @BeforeAll
    public static void beforeAll() throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection("hospital");
        if (SystemUtils.getSystemPropertyDatabase().equals("h2")) {
            DatabaseUtils.h2CreateHospitalData();
        }
        if (SystemUtils.getSystemPropertyDatabase().equals("mysql")) {
            DatabaseUtils.mysqlCreateHospitalData();
        }
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        DatabaseConnection.closeConnection();
    }

    @Test
    @Description("Show first name, last name, and gender of patients whose gender is 'M'")
    public void testQuestion1() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, gender from hospital.patients where gender='M'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show first name and last name of patients who does not have allergies. (null)")
    public void testQuestion2() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name from hospital.patients where allergies is null");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show first name of patients that start with the letter 'C'")
    public void testQuestion3() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name from hospital.patients where first_name like 'C%'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show first name and last name of patients that weight within the range of 100 to 120 (inclusive)")
    public void testQuestion4() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name from hospital.patients where weight between 100 and 120");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Update the patients table for the allergies column. If the patient's allergies is null then replace it with 'NKA'")
    public void testQuestion5() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hospital.patients SET allergies='NKA' WHERE allergies IS null");
        int rowsUpdated = preparedStatement.executeUpdate();
    }

    @Test
    @Description("Show first name and last name concatinated into one column to show their full name.")
    public void testQuestion6() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select CONCAT(first_name,' ', last_name) AS full_name from hospital.patients");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show first name, last name, and the full province name of each patient.
            Example: 'Ontario' instead of 'ON'""")
    public void testQuestion7() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, province_name from hospital.patients LEFT JOIN hospital.province_names ON patients.province_id=province_names.province_id");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show how many patients have a birth_date with 2010 as the birth year.")
    public void testQuestion8() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) AS total_patients from hospital.patients WHERE year(birth_date)=2010");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the first_name, last_name, and height of the patient with the greatest height.")
    public void testQuestion9() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, height from hospital.patients ORDER BY height DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show all columns for patients who have one of the following patient_ids: 1,45,534,879,1000")
    public void testQuestion10() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from hospital.patients where patient_id in (1,45,534,879,1000)");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the total number of admissions")
    public void testQuestion11() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from hospital.admissions");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show all the columns from admissions where the patient was admitted and discharged on the same day.")
    public void testQuestion12() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from hospital.admissions where admission_date=discharge_date");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the patient id and the total number of admissions for patient_id 579.")
    public void testQuestion13() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select patient_id, count(*) from hospital.admissions where patient_id=579 group by patient_id");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Based on the cities that our patients live in, show unique cities that are in province_id 'NS'.")
    public void testQuestion14() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select distinct city from hospital.patients where province_id='NS'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Write a query to find the first_name, last name and birth date of patients who has height greater than 160 and weight greater than 70")
    public void testQuestion15() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, birth_date from hospital.patients where height>160 and weight>70");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Write a query to find list of patients first_name, last_name, and allergies where allergies are not null and are from the city of 'Hamilton'")
    public void testQuestion16() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, allergies from hospital.patients where allergies is not null and city like 'Hamilton'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show unique birth years from patients and order them by ascending.")
    public void testQuestion17() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select distinct year(birth_date) from hospital.patients order by year(birth_date) asc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show unique first names from the patients table which only occurs once in the list.
            For example, if two or more people are named 'John' in the first_name column then don't include their name in the output list. If only 1 person is named 'Leo' then include them in the output.""")
    public void testQuestion18() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name from (select first_name, count(*) as amount from hospital.patients group by first_name) as temp where temp.amount=1");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show patient_id and first_name from patients where their first_name start and ends with 's' and is at least 6 characters long.")
    public void testQuestion19() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select patient_id, first_name from hospital.patients where first_name like 's%____%s'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show patient_id, first_name, last_name from patients whos diagnosis is 'Dementia'.
            Primary diagnosis is stored in the admissions table.""")
    public void testQuestion20() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select patients.patient_id, first_name, last_name from hospital.patients left join hospital.admissions on patients.patient_id=admissions.patient_id where diagnosis like 'Dementia'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Display every patient's first_name.
            Order the list by the length of each name and then by alphabetically.""")
    public void testQuestion21() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name from hospital.patients order by length(first_name), first_name asc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show the total amount of male patients and the total amount of female patients in the patients table.
            Display the two results in the same row.""")
    public void testQuestion22() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select sum(gender='M') as male_count, sum(gender='F') as female_count from hospital.patients");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show first and last name, allergies from patients which have allergies to either 'Penicillin' or 'Morphine'. Show results ordered ascending by allergies then by first_name then by last_name.")
    public void testQuestion23() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select  first_name,  last_name,  allergies from hospital.patients where allergies in ('Penicillin', 'Morphine') order by allergies, first_name, last_name");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show patient_id, diagnosis from admissions. Find patients admitted multiple times for the same diagnosis.")
    public void testQuestion24() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select patient_id, diagnosis from hospital.admissions group by patient_id, diagnosis having count(*)>1");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show the city and the total number of patients in the city.
            Order from most to least patients and then by city name ascending.""")
    public void testQuestion25() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select city, count(*) as num_patients from hospital.patients group by city order by num_patients desc, city asc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show first name, last name and role of every person that is either patient or doctor.
            The roles are either "Patient" or "Doctor\"""")
    public void testQuestion26() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, 'Patient' as role from hospital.patients union all select first_name, last_name, 'Doctor' as role from hospital.doctors");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show all allergies ordered by popularity. Remove 'NKA' and NULL values from query.")
    public void testQuestion27() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select allergies, count(*) as total_diagnosis from hospital.patients where allergies is not null group by allergies order by total_diagnosis desc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show all patient's first_name, last_name, and birth_date who were born in the 1970s decade. Sort the list starting from the earliest birth_date.")
    public void testQuestion28() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, birth_date from hospital.patients where year(birth_date) between 1970 and 1979 order by birth_date asc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("We want to display each patient's full name in a single column. Their last_name in all upper letters must appear first, then first_name in all lower case letters. Separate the last_name and first_name with a comma. Order the list by the first_name in decending order. EX: SMITH,jane")
    public void testQuestion29() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select concat(upper(last_name),',',lower(first_name)) as full_name from hospital.patients order by first_name desc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the province_id(s), sum of height; where the total sum of its patient's height is greater than or equal to 7,000.")
    public void testQuestion30() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select province_id, sum(height) as sum_height from hospital.patients group by province_id having sum_height>=7000");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the difference between the largest weight and smallest weight for patients with the last name 'Maroni'")
    public void testQuestion31() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select max(weight)-min(weight) as weight_delta from hospital.patients where last_name like 'Maroni'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show all of the days of the month (1-31) and how many admission_dates occurred on that day. Sort by the day with most admissions to least admissions.")
    public void testQuestion32() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select day(admission_date) as day_number, count(*) as number_of_admissions from hospital.admissions group by day_number order by number_of_admissions desc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the all columns for patient_id 542's most recent admission_date.")
    public void testQuestion33() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from hospital.admissions WHERE patient_id = 542 AND admission_date = (select MAX(admission_date) from hospital.admissions WHERE patient_id = 542)");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show patient_id, attending_doctor_id, and diagnosis for admissions that match one of the two criteria:
            1. patient_id is an odd number and attending_doctor_id is either 1, 5, or 19.
            2. attending_doctor_id contains a 2 and the length of patient_id is 3 characters.""")
    public void testQuestion34() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select patient_id, attending_doctor_id, diagnosis from hospital.admissions where (patient_id % 2 = 1 and attending_doctor_id in (1, 5, 19)) or (attending_doctor_id like '%2%' and length(patient_id) = 3)");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show first_name, last_name, and the total number of admissions attended for each doctor.")
    public void testQuestion35() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select doctors.first_name, doctors.last_name, count(*) as total_admissions from hospital.admissions left join hospital.doctors on admissions.attending_doctor_id=doctors.doctor_id where doctor_id is not null and admission_date is not null group by doctor_id");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("For each doctor, display their id, full name, and the first and last admission date they attended.")
    public void testQuestion36() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select doctors.doctor_id, concat (first_name, ' ', last_name) as full_name, min(admission_date) as first_admission_date, MAX(admission_date) as last_admission_date from hospital.doctors left join hospital.admissions on doctors.doctor_id = admissions.attending_doctor_id group by doctors.doctor_id");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Display the total amount of patients for each province. Order by descending.")
    public void testQuestion37() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select province_name, count(*) as patients_count from hospital.province_names RIGHT join hospital.patients on province_names.province_id = patients.province_id group by province_name order by patients_count desc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("For every admission, display the patient's full name, their admission diagnosis, and their doctor's full name who diagnosed their problem.")
    public void testQuestion38() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select concat(patients.first_name, ' ', patients.last_name) as patient_name, diagnosis, concat(doctors.first_name,' ', doctors.last_name) as doctor_name from hospital.patients left join hospital.admissions on patients.patient_id = admissions.patient_id left join hospital.doctors on admissions.attending_doctor_id = doctors.doctor_id where diagnosis is not null");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Display the first name, last name and number of duplicate patients based on their first name and last name.")
    public void testQuestion39() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name, count(*) as duplicates from hospital.patients group by first_name,last_name having duplicates>1");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Display patient's full name,
             height in the unit feet rounded to 1 decimal,
             weight in the unit pounds rounded to 0 decimals,
             birth_date,
             gender non abbreviated.
            Convert CM to feet by dividing by 30.48.
            Convert KG to pounds by multiplying by 2.205.""")
    public void testQuestion40() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select concat(first_name, ' ', last_name) as name, round(height/30.48,1) as height, round(weight*2.205,0) as weight, birth_date, (case when gender='M' then 'MALE' else 'FEMALE' end) from hospital.patients");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show patient_id, first_name, last_name from patients whose does not have any records in the admissions table. (Their patient_id does not exist in any admissions.patient_id rows.)")
    public void testQuestion41() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select patients.patient_id, first_name, last_name from hospital.patients left join hospital.admissions on patients.patient_id=admissions.patient_id where admission_date is null");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Display a single row with max_visits, min_visits, average_visits where the maximum, minimum and average number of admissions per day is calculated. Average is rounded to 2 decimal places.")
    public void testQuestion42() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select max(visits) as max_visits, min(visits) as min_visits, round(avg(visits),2) as average_admissions from (select count(*) as visits from hospital.admissions group by admission_date) as temp");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show all of the patients grouped into weight groups.
            Show the total amount of patients in each weight group.
            Order the list by the weight group decending.
            For example, if they weight 100 to 109 they are placed in the 100 weight group, 110-119 = 110 weight group, etc.""")
    public void testQuestion43() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as patients_in_group, weight/10*10 as weight_group from hospital.patients group by weight_group order by weight_group desc");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show patient_id, weight, height, isObese from the patients table.
            Display isObese as a boolean 0 or 1.
            Obese is defined as weight(kg)/(height(m)2) >= 30.
            weight is in units kg.
            height is in units cm.""")
    public void testQuestion44() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select patient_id, weight, height, case when weight/power(round(height/100.0,2),2)>=30 then 1 else 0 end as isObese from hospital.patients");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Show patient_id, first_name, last_name, and attending doctor's specialty.
            Show only the patients who has a diagnosis as 'Dementia' and the doctor's first name is 'Lisa'
            Check patients, admissions, and doctors tables for required information.""")
    public void testQuestion45() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select T1.patient_id, T1.first_name, T1.last_name, T1.specialty from (select p.patient_id, p.first_name, p.last_name, d.specialty, a.diagnosis, d.first_name as doctor_first_name from hospital.patients as p left join hospital.admissions as a on p.patient_id = a.patient_id left join hospital.doctors as d on a.attending_doctor_id = d.doctor_id where a.diagnosis like 'Epilepsy' and d.first_name like 'Lisa') as T1");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            All patients who have gone through admissions, can see their medical documents on our site. Those patients are given a temporary password after their first admission. Show the patient_id and temp_password.
            The password must be the following, in order:
            1. patient_id
            2. the numerical length of patient's last_name
            3. year of patient's birth_date""")
    public void testQuestion46() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select distinct p.patient_id, concat(p.patient_id,length(p.last_name),year(p.birth_date)) as temp_password from hospital.patients as p right join hospital.admissions as a on p.patient_id=a.patient_id");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            Each admission costs $50 for patients without insurance, and $10 for patients with insurance. All patients with an even patient_id have insurance.
            Give each patient a 'Yes' if they have insurance, and a 'No' if they don't have insurance. Add up the admission_total cost for each has_insurance group.""")
    public void testQuestion47() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select T1.has_insurance, sum(T1.admission_cost) as admission_total_cost from (select patient_id, case when patient_id % 2 = 0 then 'Yes' else 'No' end as has_insurance, case when patient_id % 2 = 0 then 10 else 50 end as admission_cost from hospital.admissions) as T1 group by has_insurance");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the provinces that has more patients identified as 'M' than 'F'. Must only show full province_name")
    public void testQuestion48() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select province_name from hospital.patients left join hospital.province_names on patients.province_id = province_names.province_id group by province_name having sum(case when gender like 'M' then 1 else -1 end)>0");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("""
            We are looking for a specific patient. Pull all columns for the patient who matches the following criteria:
            - First_name contains an 'r' after the first two letters.
            - Identifies their gender as 'F'
            - Born in February, May, or December
            - Their weight would be between 60kg and 80kg
            - Their patient_id is an odd number
            - They are from the city 'Kingston'""")
    public void testQuestion49() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from hospital.patients where first_name like '__r%' and gender like 'F' and month(birth_date) in (2, 5, 12) and weight between 60 and 81 and patient_id % 2 = 1 and city like 'Kingston'");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("Show the percent of patients that have 'M' as their gender. Round the answer to the nearest hundreth number and in percent form.")
    public void testQuestion50() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select concat(round(100.0*count(gender like 'M')/count(*),2),'%') as percent_of_male_patients from hospital.patients");
        ResultSet resultSet = preparedStatement.executeQuery();
    }
//
//    @Test
//    @Description("For each day display the total amount of admissions on that day. Display the amount changed from the previous date.")
//    public void testQuestion51() throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("");
//        ResultSet resultSet = preparedStatement.executeQuery();
//    }

    @Test
    @Description("Sort the province names in ascending order in such a way that the province 'Ontario' is always on top.")
    public void testQuestion52() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select province_name from hospital.province_names order by (case when province_name like 'Ontario' then 1 else 2 end), province_name");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

    @Test
    @Description("We need a breakdown for the total amount of admissions each doctor has started each year. Show the doctor_id, doctor_full_name, specialty, year, total_admissions for that year.")
    public void testQuestion53() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select doctor_id, concat(first_name,' ', last_name) as doctor_name, specialty, year(admission_date) as selected_year, count(*) as total_admissions from hospital.doctors left join hospital.admissions on doctors.doctor_id=admissions.attending_doctor_id group by doctors.doctor_id, selected_year");
        ResultSet resultSet = preparedStatement.executeQuery();
    }
}
