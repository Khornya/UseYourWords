import * as React from "react";

interface IGameRoomProps {
  gameId: string;
}

class GameRoom extends React.Component<IGameRoomProps> {
  render = () => {
    return (
    <div>Welcome in the game {this.props.gameId} !</div>
    )
  }
}

export default GameRoom;
