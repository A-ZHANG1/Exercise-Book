package design;

import java.util.*;

/**
 * Created by Wayne.A.Z on 2020-07-06. 小小系统设计
 */
public class Twitter {
    private int timestamp = 0;
    private PriorityQueue<Tweet> maxHeap;
    private Map<Integer, Set<Integer>> following;// < userId, (set) followingList >
    private Map<Integer, Tweet> twitter; // < userId, head of Tweet LinkedList >

    private class Tweet{
        private int id;
        private int timestamp;
        private Tweet next;

        public Tweet(int id, int timestamp){
            this.id = id;
            this. timestamp = timestamp;
        }
    }

    /** Initialize your data structure here. */
    public Twitter() {
        maxHeap = new PriorityQueue<>((o1, o2) -> o2.timestamp - o1.timestamp);
        following = new HashMap<>();
        twitter = new HashMap<>();
    }
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        timestamp++;
        Tweet tweet = new Tweet(tweetId, timestamp);
        if (twitter.containsKey(userId)) {
            Tweet oldHead = twitter.get(userId);
            tweet.next = oldHead;
            twitter.put(userId, tweet);

        } else {
            twitter.put(userId, tweet);
        }
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user followed or by the user herself.
     * Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        // 将所有可能tweet入maxHeap
        maxHeap.clear();
        if (twitter.containsKey(userId)) {
            maxHeap.add(twitter.get(userId));
        }

        Set<Integer> followingList = following.get(userId);
        if (followingList != null && followingList.size() != 0) { // 判断列表不为空
            for (Integer followingId : followingList) {
                Tweet t = twitter.get(followingId);
                if (t != null) {
                    maxHeap.add(t);
                }
            }
        }
//        System.out.println(Arrays.toString(maxHeap.toArray()));


        // 写入res
        List<Integer> res = new ArrayList<>(10);
        int cnt = 0;
        while (!maxHeap.isEmpty() && cnt < 10) {
            res.add(maxHeap.poll().id);
            cnt++;
        }
        return res;
    }

    /**
     * Follower follows a followee.
     * If the operation is invalid, it should be a no-op.
     *
     * @param followerId 发起关注者 id
     * @param followeeId 被关注者 id
     */
    public void follow(int followerId, int followeeId) {
        // Invalid op
        if (followerId == followeeId) {
            return;
        }

        Set<Integer> followingList = following.get(followerId);
        if (followingList == null) {
            Set<Integer> init = new HashSet<>(Arrays.asList(followeeId));
//            init.add(followeeId);
            following.put(followerId, init);
        } else {
            if (followingList.contains(followeeId)) {
                return;
            }
            followingList.add(followeeId);
        }
    }
    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     *
     * @param followerId 发起取消关注的人的 id
     * @param followeeId 被取消关注的人的 id
     */
    public void unfollow(int followerId, int followeeId) {
        if(followeeId == followerId) return;
        Set<Integer> followingList = following.get(followerId);
        followingList.remove(followeeId);
    }

    public static void main(String[] args){
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 1);
        twitter.postTweet(1, 5);
        List<Integer> res1 = twitter.getNewsFeed(1);
        System.out.println(res1);

        twitter.follow(2, 1);

        List<Integer> res2 = twitter.getNewsFeed(2);
        System.out.println(res2);

        twitter.unfollow(2, 1);

        List<Integer> res3 = twitter.getNewsFeed(2);
        System.out.println(res3);

    }
}

