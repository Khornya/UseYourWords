export interface IMessage {
  type: "ERROR" | "JOINED" | "PLAYER_JOINED" | "START" | "PLAYER_LEFT" | "GAME_OVER" | "NEXT_ROUND";
  content: IMessageContent;
}

export interface IMessageContent {}

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
