package trainerapp.domain;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.util.List;

public class MidiService {

    public void openMidiDevice(TrainerSession session) {
        MidiDevice device;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; i++) {
            try {
                device = MidiSystem.getMidiDevice(infos[i]);
                System.out.println(infos[i]);

                List<Transmitter> transmitters = device.getTransmitters();

                for (Transmitter transmitter : transmitters) {
                    transmitter.setReceiver(
                            new MidiInputReceiver(session)
                    );
                }

                Transmitter transmitter = device.getTransmitter();
                transmitter.setReceiver(new MidiInputReceiver(session));

                device.open();

            } catch (MidiUnavailableException ignored) {
            }
        }

    }

}
