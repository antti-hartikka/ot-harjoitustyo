package trainerapp.ui;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.util.List;

/**
 * This class provides possibility to open and close midi inputs
 */
public class MidiService {

    private MidiDevice device;

    /**
     *
     * @param trainer Trainer class for midi inputs to send notes
     */
    public void openMidiDevice(Trainer trainer) {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                device = MidiSystem.getMidiDevice(info);

                List<Transmitter> transmitters = device.getTransmitters();

                for (Transmitter transmitter : transmitters) {
                    transmitter.setReceiver(
                            new MidiInputReceiver(trainer)
                    );
                }

                Transmitter transmitter = device.getTransmitter();
                transmitter.setReceiver(new MidiInputReceiver(trainer));

                device.open();

            } catch (MidiUnavailableException ignored) {
            }
        }

    }

    /**
     * closes all midi inputs
     */
    public void closeMidiDevice() {
        device.close();
    }

}
