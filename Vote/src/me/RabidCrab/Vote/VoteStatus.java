package me.RabidCrab.Vote;

/**
 * Votes can have 3 states. The third one, Inconclusive, is used when the vote is not finished and cannot yet be
 * determined to fail or succeed
 * @author Rabid
 *
 */
public enum VoteStatus
{
    Success
    , Fail
    , Inconclusive
}
