const { connect, addStudent, getStudent, averageGrade } = require("./students");

let client;

beforeAll(async () => { client = await connect(); });
afterAll(async () => { await client.end(); });
// Avant chaque test, la table est vidée.
beforeEach(async () => { await client.query("TRUNCATE students RESTART IDENTITY"); });

test("ajouter puis lire un étudiant", async () => {
  const id = await addStudent(client, "Amel", 15);
  expect(await getStudent(client, id)).toEqual({ name: "Amel", grade: 15 });
});

test("lire un étudiant inexistant renvoie null", async () => {
  expect(await getStudent(client, 9999)).toBeNull();
});

test("la moyenne de 10 et 14 vaut 12", async () => {
  await addStudent(client, "Amel", 10);
  await addStudent(client, "Karim", 14);
  expect(await averageGrade(client)).toBe(12);
});