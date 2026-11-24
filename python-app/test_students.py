import pytest
from students import connect, add_student, get_student, average_grade


@pytest.fixture
def conn():
  
    c = connect()
    with c.cursor() as cur:
        cur.execute("TRUNCATE students RESTART IDENTITY")
    c.commit()
    yield c
    c.close()


def test_ajouter_et_lire(conn):
    student_id = add_student(conn, "Amel", 15)      
    student = get_student(conn, student_id)         
    assert student == ("Amel", 15.0)              


def test_etudiant_inexistant(conn):
    assert get_student(conn, 9999) is None


def test_moyenne(conn):
    add_student(conn, "Amel", 10)
    add_student(conn, "Karim", 14)
    assert average_grade(conn) == 13.0