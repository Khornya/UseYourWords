export interface IMessage {
  type: "ERROR" | "JOINED" | "PLAYER_JOINED" | "START" | "PLAYER_LEFT" | "GAME_OVER" | "NEXT_ROUND" | "TIMER" | "END_ROUND" | "VOTES" | "HIDE_ANSWER";
  content: IMessageContent;
}

export interface IMessageContent { }

export interface IErrorMessageContent extends IMessageContent {
  code: string;
  message: string;
}

export interface IJoinedMessageContent extends IMessageContent {
  gameId: string;
  index: number
  name: string
}

export interface IHideAnswerMessageContent extends IMessageContent {
  answerIndex: number
}

export interface IPlayerJoinedMessageContent extends IMessageContent {
  name: string
  index: number
}

export interface ICreateMessagePayload {
  name: string;
  numOfPlayers: number;
  numOfTeams: number;
  numOfRounds: number;
}

export interface IAnswerMessagePayload {
  type: "PHOTO" | "VIDEO" | "TEXT"
  answers: string[]
  playerIndex: number
}

export interface IStartMessageContent {
  teams: Team[]
}

export interface IGameRoundMessageContent {
  roundNumber: number
  element: Element
}

export interface ITimerMessageContent {
  secondsLeft: number
}

export interface IEndRoundMessageContent {
  answers: string[]
}

export interface Team {
  maxNumOfPlayers: number,
  currentNumOfPlayers: number,
  score: number,
  scoreDiff: number,
  players: Player[]
  number: number
}

export interface Player {
  name: string,
  sessionId: string
}

export interface Element {
  id: number
  name: string
  url: string
  type: "PHOTO" | "VIDEO" | "TEXT"
}

export interface IGameState {
  teams: Team[]
  element: Element
  roundNumber: number
  showTimer: boolean
  displayAnswerForm: boolean
  displayVoteForm: boolean
  displayVoteResult: boolean
  answers: string[]
  gameOver: boolean
}