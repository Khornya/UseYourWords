import * as React from "react";

interface IWaitingMessageProps {
  message: string
}

class WaitingMessage extends React.Component<IWaitingMessageProps> {
  render = () => {
    return (
      <div className="waitingMessage">
        <div className="spinner-border text-primary" role="status">
          <span className="sr-only"></span>
        </div>
        <div className="message">{this.props.message}</div>
      </div>
    );
  };
}

export default WaitingMessage;
