package net.flandre923.tutorialmod.client;

public class ClientThirstData {
    public static int playerThirst;

    public static void set(int thirst){
        ClientThirstData.playerThirst = thirst;
    }

    public static int getPlayerThirst(){
        return playerThirst;
    }
}
