import * as React from "react";

interface IWaitingRoomProps {
    gameId: string
}

class WaitingRoom extends React.Component<IWaitingRoomProps> {
    render = () => {
        return (
        <div>Ask your friends to join game with code {this.props.gameId}</div>
        );
    }
}

export default WaitingRoom;
