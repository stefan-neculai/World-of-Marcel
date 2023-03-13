import java.util.*;

public class Account {
    public Information accountInfo;
    public List<Character> characterList;
    public int mapsCompleted;

    public Account(int mapsCompleted) {
        this.characterList = new ArrayList<Character>();
        this.mapsCompleted = mapsCompleted;
    }
    static class Information {
        private Credentials data;
        private PriorityQueue<String> favoriteGames;
        private String name;
        public String country;

        private Information(Builder builder) {
            data = builder.data;
            favoriteGames = builder.favoriteGames;
            country = builder.country;
            name = builder.name;
        }

        public Credentials getData() {
            return data;
        }
        public String getName() {
            return name;
        }

        public static class Builder {
            private Credentials data;
            private PriorityQueue<String> favoriteGames;
            private String name;
            private String country;

            public Builder(Credentials data, String name) {
                this.data = data;
                this.name = name;
            }

            public Builder favoriteGames(Collection<String> favoriteGames) {
                this.favoriteGames = (PriorityQueue<String>) favoriteGames;
                return this;
            }

            public Builder country(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }
}
