package trainerapp.domain;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import java.util.Arrays;

public class MidiInputReceiver implements Receiver {

    private TrainerSession trainerSession;

    public MidiInputReceiver(TrainerSession trainerSession) {
        this.trainerSession = trainerSession;
    }

    public void send(MidiMessage msg, long timeStamp) {
        if (msg.getMessage()[0] == -112) {
            trainerSession.noteInput(msg.getMessage()[1]);
        }
    }

    public void close() {
    }
}

