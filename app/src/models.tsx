export interface IMessage {
  type: "ERROR" | "JOINED";
  content: IMessageContent;
}

export interface IMessageContent {}

export interface IErrorMessageContent extends IMessageContent {
  code: string;
  message: string;
}

export interface IJoinedMessageContent extends IMessageContent {
  gameId: string;
}

export interface ICreateMessagePayload {
  name: string;
  numOfPlayers: number;
  numOfTeams: number;
  numOfRounds: number;
}
