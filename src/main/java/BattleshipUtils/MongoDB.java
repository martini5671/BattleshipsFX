package BattleshipUtils;

import com.mongodb.client.*;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static com.mongodb.client.model.Indexes.descending;

public class MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDB(String connection_string, String database_name, String collection_name) {
        this.mongoClient = MongoClients.create(connection_string);
        this.database = mongoClient.getDatabase(database_name);
        this.collection = database.getCollection(collection_name);
    }

    public void insertStats(String winner, String looser, int score, int gameplay_time, int winner_misses) {
        // Get the current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);

        Document document = new Document();

        //create document:
        document.append("Winner", winner);
        document.append("Looser", looser);
        document.append("Score", score);
        document.append("DateOfGame", formattedDate);
        document.append("TotalGameplayTime", gameplay_time);
        document.append("NumberOfMissesByWinner", winner_misses);

        collection.insertOne(document);
    }

    public void printAllDocuments() {
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    public void deleteCollection() {
        collection.drop();
    }

    public HashMap<String, ArrayList<String>> fetchSortedDataByScore(int n_records) {
        //hashmap
        HashMap<String, ArrayList<String>> dataHashMap = new HashMap<>();
        // arrays
        ArrayList<String> winners = new ArrayList<>();
        ArrayList<String> losers = new ArrayList<>();
        ArrayList<String> scores = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();
        ArrayList<String> misses = new ArrayList<>();
        //cursor
        MongoCursor<Document> cursor = collection.find().sort(descending("Score")).iterator();
        int counter = 0;
        try {
            while (cursor.hasNext() && counter < n_records) {
                Document doc = cursor.next();
                String winner = (String) doc.get("Winner");
                String looser = (String) doc.get("Looser");
                Integer score = (Integer) doc.get("Score");
                String dateOfGame = (String) doc.get("DateOfGame");
                Integer time = (Integer) doc.get("TotalGameplayTime");
                Integer miss = (Integer) doc.get("NumberOfMissesByWinner");
                // add to arrays
                winners.add(winner);
                losers.add(looser);
                scores.add(String.valueOf(score));
                dates.add(dateOfGame);
                times.add(String.valueOf(time));
                misses.add(String.valueOf(miss));
                counter++;
            }
        } finally {
            cursor.close();
        }
        // add everything to hashmap:
        dataHashMap.put("winners", winners);
        dataHashMap.put("losers", losers);
        dataHashMap.put("scores", scores);
        dataHashMap.put("dates", dates);
        dataHashMap.put("times", times);
        dataHashMap.put("misses", misses);
        return dataHashMap;
    }

    public static void printSortedHashMap(HashMap<String, ArrayList<String>> hashmap) {
        for (Map.Entry<String, ArrayList<String>> entry : hashmap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        //getmongoExample();
        MongoDB mongoDB = new MongoDB("mongodb://localhost:27017/", "battleships_scores", "test2");

        mongoDB.deleteCollection();
        //make insert
        for (int i = 0; i < 10; i++) {
            mongoDB.insertStats(("Player" + i), "MidAI", 10000 - i * 100, 230, 123);
        }
        //print all documents
        mongoDB.printAllDocuments();
        // show sorted hashmap
        mongoDB.fetchSortedDataByScore(5);
    }

}
