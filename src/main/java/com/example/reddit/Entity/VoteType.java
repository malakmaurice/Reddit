package com.example.reddit.Entity;

public enum VoteType {
    DOWNVOTE(1),UPVOTE(-1);

    private int direction;

    public int getDirection() {
        return direction;
    }
    private VoteType(int direction) {
        this.direction=direction;
    }
}
