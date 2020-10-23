package com.github.khornya.useyourwords.model;

import com.github.khornya.useyourwords.service.ElementService;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    @Value(value = "${websocket.gameroom.config.numof.rounds}")
    protected int defaultNumOfRounds;

    @Value(value = "${websocket.gameroom.config.numof.player}")
    protected int defaultNumOfPlayers;

    @Value(value = "${websocket.gameroom.config.numof.teams}")
    protected int defaultNumOfTeams;

    private ElementService elementService;
    private String id;
    private Player[] players;
    private Team[] teams;
    private int numOfRounds;
    private int currentRoundNumber = 0;
    private ArrayList<Answer> answers = new ArrayList<>();
    private ArrayList<Vote> votes = new ArrayList<>();
    private Set<String> readyPlayers = new HashSet<>();
    private AtomicInteger joinCount = new AtomicInteger(0);
    private ArrayList<Element> elements;
    private Element currentElement;
    private boolean acceptAnswers = false;
    private Timer timer;

    public Game(ElementService elementService) {
        this.elementService = elementService;
        this.id = UUID.randomUUID().toString();
        this.players = new Player[defaultNumOfPlayers];
        this.teams = new Team[defaultNumOfTeams];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(defaultNumOfPlayers / defaultNumOfTeams, i);
        }
        this.numOfRounds = defaultNumOfRounds;
        initializeElements();
    }

    public Game(ElementService elementService, String id, int numOfPlayers, int numOfTeams, int numOfRounds) {
        this.elementService = elementService;
        this.id = id;
        this.players = new Player[numOfPlayers];
        this.teams = new Team[numOfTeams];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(numOfPlayers / numOfTeams, i);
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

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
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

    public void nextRound() {
        currentRoundNumber++;
        this.answers = new ArrayList<>();
        this.votes = new ArrayList<>();
        this.currentElement = elements.remove(0);
        ArrayList<String> predefinedAnswerAsArray = new ArrayList<>();
        predefinedAnswerAsArray.add(this.currentElement.getDefaultResponse());
        this.answers.add(new Answer(predefinedAnswerAsArray, -1, this.currentElement.getType()));
    }

    private void initializeElements() {
        List<Element> videoElements = new ArrayList<>();
        List<Element> photoElements = new ArrayList<>();
        List<Element> textElements = new ArrayList<>();
        for (int i = 0; i < numOfRounds / 3; i++) {
            videoElements.add(elementService.findRandomElementByType(ElementType.VIDEO));
        }
        for (int i = 0; i < numOfRounds / 3; i++) {
            photoElements.add(elementService.findRandomElementByType(ElementType.PHOTO));
        }
        for (int i = 0; i < numOfRounds / 3; i++) {
            textElements.add(elementService.findRandomElementByType(ElementType.TEXT));
        }
        Random random = new Random();
        elements = new ArrayList<>();
        for (int i = 0; i < numOfRounds / 3; i++) {
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
        for (Answer answer : answers) {
            if (answer.getType() == ElementType.TEXT && answer.getPlayerIndex() != -1) {
                String transformedAnswer = this.currentElement.getToFillText();
                for (String part : answer.getAnswers()) {
                    transformedAnswer = transformedAnswer.replaceFirst("\\[\\.\\.\\.\\]", part);
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

    public void addVote(int answerIndex, String sessionId) {
        int teamIndex = 0;
        for (int i = 0; i < this.teams.length; i++) {
            for (Player player : this.teams[i].getPlayers()) {
                if (player.getSessionId().equals(sessionId)) {
                    teamIndex = i;
                }
            }
        }
        this.votes.add(new Vote(answerIndex, teamIndex));
    }

    public Team[] processVotes() {
        int[] oldScores = new int[this.teams.length];
        for (int i = 0; i < this.teams.length; i++) {
            oldScores[i] = this.teams[i].getScore();
        }
        int[] counts = new int[this.answers.size()];
        Arrays.fill(counts, 0);
        for (Vote vote : this.votes) {
            counts[vote.getAnswerIndex()]++;
        }
        int max = 0;
        int fakeAnswerIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > max) {
                max = counts[i];
            }
            if (this.answers.get(i).getPlayerIndex() == -1) {
                fakeAnswerIndex = i;
            }
        }
        for (Vote vote : this.votes) {
            if (vote.getAnswerIndex() == fakeAnswerIndex) {
                Team looser = this.teams[vote.getTeamIndex()];
                looser.setScore(looser.getScore() - 500);
            }
        }
        for (int i = 0; i < this.answers.size(); i++) {
            if (counts[i] == max && i != fakeAnswerIndex) {
                Team winner = this.teams[this.answers.get(i).getPlayerIndex() / this.teams[0].getPlayers().length];
                winner.setScore(winner.getScore() + 1000);
            }
        }
        for (int i = 0; i < this.teams.length; i++) {
            this.teams[i].setScoreDiff(this.teams[i].getScore() - oldScores[i]);
        }
        return this.teams;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", players=" + Arrays.toString(players) + ", readyPlayerSet=" + readyPlayers + ", joinCount=" + joinCount + "]";
    }
}
