# SEG2505 – Introduction au Génie Logiciel
## Rapport de Laboratoire 6 : SQLite et Android

**Nom de l'étudiant :** Adam Achaba  
**Numéro d'étudiant :** [Indiquez votre numéro d'étudiant]  
**Dépôt GitHub :** https://github.com/AdamAchaba/SEG2505_Lab6_SQLite  
**Date :** 20 Octobre 2026  

---

## 1. Pratique de SQL (Diapositive 20 – À rendre)
*Tutoriel officiel Android : Utiliser SQL pour lire et écrire dans une base de données.*

Le langage SQL (*Structured Query Language*) permet d'interagir directement avec le moteur relationnel SQLite intégré à Android. Voici les requêtes fondamentales mises en œuvre pour la création et la manipulation des données :

### 1.1 Création de la table (DDL)
```sql
CREATE TABLE items (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    price REAL NOT NULL
);
```
*Explication :* Définition d'une table avec une clé primaire auto-incrémentée `_id`, un libellé textuel obligatoire `name`, et une valeur numérique `price`.

### 1.2 Insertion d'enregistrements (Create)
```sql
INSERT INTO items (name, price) VALUES ('Clavier mécanique', 89.99);
INSERT INTO items (name, price) VALUES ('Souris sans fil', 39.50);
INSERT INTO items (name, price) VALUES ('Écran 27 pouces', 249.00);
```

### 1.3 Interrogation et filtrage des données (Read)
```sql
-- Lecture de tous les éléments
SELECT * FROM items;

-- Lecture avec projection, filtre et tri
SELECT _id, name, price 
FROM items 
WHERE price > 50.0 
ORDER BY price DESC;
```

### 1.4 Mise à jour d'un enregistrement (Update)
```sql
UPDATE items 
SET price = 79.99 
WHERE name = 'Clavier mécanique';
```

### 1.5 Suppression d'enregistrements (Delete)
```sql
DELETE FROM items 
WHERE _id = 2;
```

---

## 2. Enregistrer des données avec SQLite sous Java (Diapositives 5 à 16 & 20 – À rendre)
*Tutoriel Android Developers : Implémentation native d'une base de données SQLite sous Android.*

### 2.1 Architecture logicielle mise en place
1. **Contrat de base de données (`FeedReaderContract.java`) :** Définit de façon unifiée le schéma de la table `entry` et les colonnes `title` et `subtitle`.
2. **Gestionnaire de base de données (`FeedReaderDbHelper.java`) :**
   * Hérite de `SQLiteOpenHelper`.
   * Gère le cycle de vie de la base via `onCreate()` et `onUpgrade()`.
   * Encapsule les opérations CRUD sécurisées à l'aide de requêtes paramétrées et de `ContentValues`.
3. **Activité principale (`MainActivity.java`) :** Relie les composants graphiques aux méthodes SQLite pour permettre la saisie, l'affichage et la suppression en temps réel.

### 2.2 Captures d'écran et validation des fonctionnalités (Diapositive 3)

#### Capture 1 : Insertion d'un nouvel enregistrement dans SQLite
*« J'ai testé l'insertion d'un nouvel enregistrement dans la base de données SQLite via le formulaire de l'application et la méthode insertEntry(). »*

*(Capture d'écran de l'application après appui sur le bouton AJOUTER avec Titre et Sous-titre)*

#### Capture 2 : Lecture et affichage des données stockées
*« J'ai vérifié la lecture et l'affichage des enregistrements persistés dans la table SQLite avec la méthode getAllEntries(). »*

*(Capture d'écran montrant l'affichage des données persistées dans l'application)*

#### Capture 3 : Suppression d'un enregistrement
*« J'ai testé la suppression d'un enregistrement selon son titre à l'aide de la méthode deleteEntry() et validé la mise à jour immédiate. »*

*(Capture d'écran montrant la suppression réussie de l'entrée sélectionnée)*

---

## 3. Tester et déboguer votre base de données (Diapositive 20 – À rendre)
*Tutoriel Android Developers : Validation automatisée et inspection visuelle.*

### 3.1 Tests automatisés instrumentés (JUnit)
Une suite de tests automatisés instrumentés (`FeedReaderDbTest.java`) a été rédigée et exécutée sur l'environnement Android. Ces tests valident le bon comportement du cycle CRUD complet dans une base de données isolée en mémoire (`in-memory database`) :
* `testInsertAndRead()` : Valide l'écriture d'une entrée et sa relecture exacte.
* `testDelete()` : Valide que la suppression retire correctement l'élément de la table.

*« J'ai exécuté la suite de tests instrumentés JUnit FeedReaderDbTest sur l'émulateur Android pour tester et déboguer la base de données de façon automatisée. »*

### 3.2 Débogage avec l'outil Database Inspector
L'outil **Database Inspector** intégré à Android Studio (*View > Tool Windows > App Inspection > Database Inspector*) a été utilisé pendant l'exécution de l'application pour inspecter directement les tables, visualiser les modifications en temps réel (*Live updates*) et exécuter des requêtes SQL personnalisées directement sur le terminal émulé.

---

## 4. Dépôt GitHub et Démonstration (Diapositive 19)
* Le code source complet du laboratoire est versionné et synchronisé sur le dépôt GitHub public :  
  **https://github.com/AdamAchaba/SEG2505_Lab6_SQLite**
* L'application et les tests sont prêts pour la démonstration en laboratoire auprès de l'assistant d'enseignement (TA).
