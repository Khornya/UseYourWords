package com.github.khornya.useyourwords.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    /**
     * max number of player of each room. Change by
     * websocket.gameroom.config.numof.rounds property.
     */
    @Value(value = "${websocket.gameroom.config.numof.rounds}")
    protected int defaultNumOfRounds;

    /**
     * max number of player of each room. Change by
     * websocket.gameroom.config.numof.player property.
     */
    @Value(value = "${websocket.gameroom.config.numof.player}")
    protected int defaultNumOfPlayers;

    /**
     * max number of player of each room. Change by
     * websocket.gameroom.config.numof.teams property.
     */
    @Value(value = "${websocket.gameroom.config.numof.teams}")
    protected int defaultNumOfTeams;

    private String id;
    private Player[] players;
    private Team[] teams;
    private int numOfRounds;
    private int currentRoundNumber = 0;
    private ArrayList<Answer> answers = new ArrayList<>();
    private Set<String> readyPlayers = new HashSet<>();
    private AtomicInteger joinCount = new AtomicInteger(0);
    private ArrayList<Element> elements;
    private Element currentElement;
    private boolean acceptAnswers = false;
    private Timer timer;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.players = new Player[defaultNumOfPlayers];
        this.teams = new Team[defaultNumOfTeams];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(defaultNumOfPlayers / defaultNumOfTeams);
        }
        this.numOfRounds = defaultNumOfRounds;
        initializeElements();
    }

    public Game(String id, int numOfPlayers, int numOfTeams, int numOfRounds) {
        this.id = id;
        this.players = new Player[numOfPlayers];
        this.teams = new Team[numOfTeams];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(numOfPlayers / numOfTeams);
        }
        this.numOfRounds = numOfRounds;
        initializeElements();
    }

    public int getDefaultNumOfTeams() {
        return defaultNumOfTeams;
    }

    public void setDefaultNumOfTeams(int defaultNumOfTeams) {
        this.defaultNumOfTeams = defaultNumOfTeams;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }

    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds = numOfRounds;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }

    public int getDefaultNumOfRounds() {
        return defaultNumOfRounds;
    }

    public void setDefaultNumOfRounds(int defaultNumOfRounds) {
        this.defaultNumOfRounds = defaultNumOfRounds;
    }

    public int getDefaultNumOfPlayers() {
        return defaultNumOfPlayers;
    }

    public void setDefaultNumOfPlayers(int defaultNumOfPlayers) {
        this.defaultNumOfPlayers = defaultNumOfPlayers;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public AtomicInteger getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(AtomicInteger joinCount) {
        this.joinCount = joinCount;
    }

    public void addReadyPlayer(String sessionId) {
        readyPlayers.add(sessionId);
    }

    public void removeReadyPlayer(String sessionId) {
        readyPlayers.remove(sessionId);
    }

    public int getReadyPlayersSize() {
        return readyPlayers.size();
    }

    public Set<String> getReadyPlayers() {
        return readyPlayers;
    }

    public void setReadyPlayers(Set<String> readyPlayers) {
        this.readyPlayers = readyPlayers;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public Element getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(Element currentElement) {
        this.currentElement = currentElement;
    }

    public boolean isAcceptAnswers() {
        return acceptAnswers;
    }

    public void setAcceptAnswers(boolean acceptAnswers) {
        this.acceptAnswers = acceptAnswers;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int addPlayer(Player player) {
        int i;
        for (i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
        return i;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", players=" + Arrays.toString(players) + ", readyPlayerSet=" + readyPlayers + ", joinCount=" + joinCount + "]";
    }

    public Element nextRound() {
        currentRoundNumber++;
        return elements.remove(0);
    }

    private void initializeElements() {
        //TODO : get elements from repository
        List<Element> videoElements = new ArrayList<>();
        List<Element> photoElements = new ArrayList<>();
        List<Element> textElements = new ArrayList<>();
        Element videoElement = new Element();
        videoElement.setType(ElementType.VIDEO);
        videoElement.setUrl("https://www.youtube.com/watch?v=AioVDsXidh0");
        for (int i = 0; i < numOfRounds; i++) {
            videoElements.add(videoElement);
        }
        Element photoElement = new Element();
        photoElement.setType(ElementType.PHOTO);
        photoElement.setUrl("https://cornwallfilmfestival.com/wp-content/uploads/2015/11/ST6K4.jpg");
        for (int i = 0; i < numOfRounds; i++) {
            photoElements.add(photoElement);
        }
        Element textElement = new Element();
        textElement.setType(ElementType.TEXT);
        textElement.setUrl("J'ai perdu mon [...] au [...]");
        for (int i = 0; i < numOfRounds; i++) {
            textElements.add(textElement);
        }
        Random random = new Random();
        elements = new ArrayList<>();
        for (int i = 0; i < numOfRounds; i++) {
            elements.add(photoElements.remove(random.nextInt(photoElements.size())));
            elements.add(videoElements.remove(random.nextInt(videoElements.size())));
            elements.add(textElements.remove(random.nextInt(textElements.size())));
        }
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public ArrayList<String> getTransformedAnswers() {
        ArrayList<String> result = new ArrayList<>();
        for (Answer answer: answers) {
            if (answer.getType() == ElementType.TEXT) {
                String transformedAnswer = this.currentElement.getUrl();
                for (String part : answer.getAnswers()) {
                    transformedAnswer = transformedAnswer.replace("[...]", part);
                }
                result.add(transformedAnswer);
            } else {
                result.add(answer.getAnswers().get(0));
            }
        }
        return result;
    }

    public void shuffleAnswers() {
        Collections.shuffle(this.answers);
    }

    public void cancelTimer() {
        this.timer.cancel();
        this.timer.purge();
    }
}
