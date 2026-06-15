package StacksAndQueues;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Wayne.A.Z on 2019-07-21.
 * 猫狗队列
 *
 */

public class P_1004_DogCatQueue{

    //题目给出猫狗类
    class Pet{
        private String type;

        public Pet(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    class Dog extends Pet{
        public Dog() {
            super("Dog");
        }
    }

    class Cat extends Pet{
        public Cat() {
            super("Cat");
        }
    }

    //用新类为实例盖上时间戳
    class PetEnterQueue{
        Pet pet;
        int count;

        public PetEnterQueue(Pet pet, int count) {
            this.pet = pet;
            this.count = count;
        }

        public Pet getPet() {
            return pet;
        }

        public int getCount() {
            return count;
        }
    }


    //实现add,pollAll,pollDog,pollCat,isEmpty,isDogEmpty,isCatEmpty函数
    class DogCatQueue{
        Queue<PetEnterQueue> catQueue = new LinkedList<>();
        Queue<PetEnterQueue> dogQueue = new LinkedList<>();
        int count = 0;

        public void add(Pet p){
            if(p.getType().equals("Cat")) catQueue.add(new PetEnterQueue(p,count++));
            else dogQueue.add(new PetEnterQueue(p,count++));
        }

        public Pet pollAll(){
            if(!dogQueue.isEmpty() && !catQueue.isEmpty()){
                if (dogQueue.peek().getCount() > catQueue.peek().getCount()) {
                    return catQueue.poll().getPet();
                } else {
                    return dogQueue.poll().getPet();
                }
            }else if (dogQueue.isEmpty()) {
                return catQueue.poll().getPet();
            }else if(catQueue.isEmpty()) {
                return dogQueue.poll().getPet();
            }else {
                throw new RuntimeException("Queue is empty");
            }
        }

        public Dog pollDog(){
            if(dogQueue.isEmpty()) throw new RuntimeException("Queue is empty");
            else return (Dog) dogQueue.poll().getPet();
        }

        public Cat pollCat(){
            if(catQueue.isEmpty()) throw new RuntimeException("Queue is empty");
            else return (Cat) catQueue.poll().getPet();
        }

        public boolean isEmpty(){
            return catQueue.isEmpty() && dogQueue.isEmpty();
        }

        public boolean isDogEmpty(){
            return dogQueue.isEmpty();
        }

        public boolean isCatEmpty(){
            return catQueue.isEmpty();
        }



    }


}