package nz.ac.vuw.ecs.swen225.gp20.rendering;

import javax.sound.sampled.*;
import java.io.IOException;



public class Sound{
    private Clip clip;
    private String s;
    /**
     * When constructor is made, the sound gets played
     */
    public Sound() {
       setSound("/assets/Slow-Breathing.wav");

    }

    /**
     * A public method made for a single purpose of changing the track
     */
    public void updateSound(String f){
        clip.close();
        setSound(f);
    }


    /**
     * Method made for walking sound effects
     */
    public void walk(boolean b){
        if(b){
            setSound2("/assets/walk1.wav");
        }else {
            setSound2("/assets/walk2.wav");
        }
   }

    /**
     * Method to be called when timer ends
     */
   public void end(){
       clip.close();
   }

    /**
     * Method made for playing walking sound effects
     * @param filename
     */
   private void setSound2(String filename){
        try {
            Clip clip2 = AudioSystem.getClip();
            AudioInputStream aIS = AudioSystem.getAudioInputStream(getClass().getResource(filename));
            clip2.open(aIS);
            clip2.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method made for playing background sound
     * @param filename 
     */
   private void setSound(String filename){
       try {
           clip = AudioSystem.getClip();
           AudioInputStream aIS = AudioSystem.getAudioInputStream(getClass().getResource(filename));
           clip.open(aIS);
           clip.loop(Clip.LOOP_CONTINUOUSLY);
           clip.start();
           s = filename;
       } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
           e.printStackTrace();
       }
   }
   public String currentTrack(){return s;}

}