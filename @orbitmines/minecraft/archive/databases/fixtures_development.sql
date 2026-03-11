CREATE USER 'orbitmines'@'%' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'orbitmines'@'%';

CREATE DATABASE IF NOT EXISTS orbitmines_development;
CREATE DATABASE IF NOT EXISTS statistics_development;