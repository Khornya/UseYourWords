import * as React from "react";
import WaitingMessage from "./waitingMessage";

interface IWaitingRoomProps {
    gameId: string
}

class WaitingRoom extends React.Component<IWaitingRoomProps> {
    private waitingForPlayersMessage: string = "Please wait while everyone is joining ..."

    render = () => {
        return (
        <div className="waitingRoom">
            <p className="alert alert-primary" role="alert">Ask your friends to join game with code <b>{this.props.gameId}</b></p>
            <WaitingMessage message={this.waitingForPlayersMessage}/>
        </div>
        );
    }
}

export default WaitingRoom;