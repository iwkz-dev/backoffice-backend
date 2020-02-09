# connection to server

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, scoped_session
from sqlalchemy.ext.declarative import declarative_base


def connect(user, password, host, database):
    db_connection_str = 'mysql://'+user+':'+password+'@'+host+database
    engine = create_engine(db_connection_str, echo=False)
    return engine


Base = declarative_base()
DBSession = scoped_session(sessionmaker())

user = ''  # to be confirmed
password = ''  # to be confirmed
host = ''  # to be confirmed
database = ''

engine = connect(user, password, host, database)
connection = engine.connect()
session = sessionmaker(bind=engine)()
