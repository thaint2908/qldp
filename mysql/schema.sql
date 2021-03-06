DROP DATABASE IF EXISTS dbtest;
DROP DATABASE IF EXISTS qldp;

CREATE DATABASE dbtest;
CREATE DATABASE qldp;

GRANT ALL PRIVILEGES ON qldp.* TO 'user'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON dbtest.* TO 'user'@'%' WITH GRANT OPTION;
