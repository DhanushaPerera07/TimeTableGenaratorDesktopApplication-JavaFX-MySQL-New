package TimeTableGeneratorDesktopApp.Extra.ConsecetiveSesssions;

public class ConsecetiveSessions {

        int id;
        String session1ID;
        String session2ID;


        public ConsecetiveSessions(int id, String session1ID, String session2ID) {
            this.id = id;
            this.session1ID = session1ID;
            this.session2ID = session2ID;
        }

        public int getId() {
            return id;
        }

        public String getSession1ID() {
            return session1ID;
        }

        public String getSession2ID() {
            return session2ID;
        }
}