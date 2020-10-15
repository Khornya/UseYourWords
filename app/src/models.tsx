export interface IMessage {
  type: "ERROR" | "JOINED" | "PLAYER_JOINED" | "START" | "PLAYER_LEFT" | "GAME_OVER" | "NEXT_ROUND";
  content: IMessageContent;
}

export interface IMessageContent { }

export interface IErrorMessageContent extends IMessageContent {
  code: string;
  message: string;
}

export interface IJoinedMessageContent extends IMessageContent {
  gameId: string;
  playerIndex: number
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

export interface IStartMessageContent {
  teams: Team[]
}

export interface IGameRoundMessageContent {
  roundNumber: number
  element: Element
}

export interface Team {
  maxNumOfPlayers: number,
  currentNumOfPlayers: number,
  score: number,
  players: Player[]
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