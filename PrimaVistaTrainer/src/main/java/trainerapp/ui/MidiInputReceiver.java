package trainerapp.ui;

import trainerapp.domain.TrainerSession;
import trainerapp.ui.Trainer;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import java.util.Arrays;

public class MidiInputReceiver implements Receiver {

    private Trainer trainer;

    public MidiInputReceiver(Trainer trainer) {
        this.trainer = trainer;
    }

    public void send(MidiMessage msg, long timeStamp) {
        if (msg.getMessage()[0] == -112) {
            trainer.handleMidiNote(msg.getMessage()[1]);
        }
    }

    public void close() {
    }
}

