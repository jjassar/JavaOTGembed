package ioio.examples.hello_console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PulseInput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOConnectionManager.Thread;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.pc.IOIOConsoleApp;
import ioio.lib.api.PulseInput.ClockRate;
import ioio.lib.api.PulseInput.PulseMode;


public class HelloIOIOConsole extends IOIOConsoleApp {
	private boolean ledOn_ = false;
	
	
	private boolean state= true;
	private int shotno=0;
	String res = "";
	
	
	
	//serial port init
		private Uart uart;
	    private InputStream in;
	    private OutputStream serial_out;
	    private byte receivedData[] = new byte[10];
	    private int offset = 0;
	    private Byte b;
		
		

	// Boilerplate main(). Copy-paste this code into any IOIOapplication.
	public static void main(String[] args) throws Exception {
		new HelloIOIOConsole().go(args);
		
		
		
		
		
		
	}

	@Override
	protected void run(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		boolean abort = false;
		String line;
		while (!abort && (line = reader.readLine()) != null) {
			if (line.equals("t")) {
				ledOn_ = !ledOn_;
			} else if (line.equals("n")) {
				ledOn_ = true;
			} else if (line.equals("f")) {
				ledOn_ = false;
			} else if (line.equals("q")) {
				abort = true;
			} else {
				System.out
						.println("Unknown input. t=toggle, n=on, f=off, q=quit.");
			}
		}
	}

	@Override
	public IOIOLooper createIOIOLooper(String connectionType, Object extra) {
		return new BaseIOIOLooper() {
			//private DigitalOutput led_;
			DigitalOutput out1;
			
			private PwmOutput pwmOutput_;
			private DigitalOutput led_;
			private PulseInput pulse_in;
			float time1=0;
			
			
			
			
			
			

			@Override
			protected void setup() throws ConnectionLostException,
					InterruptedException {

				out1 = ioio_.openDigitalOutput(3, DigitalOutput.Spec.Mode.OPEN_DRAIN, true);
					

					
	//toggle led state
					led_ = ioio_.openDigitalOutput(IOIO.LED_PIN, true);
					
	//pulse input used to capture signal
					pulse_in = ioio_.openPulseInput(new DigitalInput.Spec(7, DigitalInput.Spec.Mode.PULL_UP), ClockRate.RATE_16MHz, PulseMode.NEGATIVE, true);
					
			
					
	//serial port init
					 uart = ioio_.openUart(48, 47, 38400, Uart.Parity.NONE,
				        Uart.StopBits.ONE);
				        in = uart.getInputStream();
				        serial_out = uart.getOutputStream();
					
					
				
				
				
				
			}

			@Override
			public void loop() throws ConnectionLostException,
					InterruptedException {
				
				try {
							//send reset
							out1.write(false);
							led_.write(false);
							//sleep(5000);
							out1.write(true);
							led_.write(true);
			
					
					


					time1 = pulse_in.waitPulseGetDuration();
					shotno++;
					time1  =  time1 * 1000000;
					time1 = 309270 / time1;
					
					if(time1>1)
					{
					
					try {
						
						String abc = String.valueOf(time1);
						abc = abc.substring(0, 6);
						byte[] outd = abc.getBytes();
						
		                serial_out.write(outd);
		                
		                try {
		                    Thread.sleep(20000);
		                } catch (InterruptedException e) {
		                    // Ignore
		                }
		            } catch (IOException e) {
		                // TODO ???
		            }
					
					
					}

					state = !state;
					led_.write(state);
					//toggleButton_.setChecked(false);
					//sleep(3000);
					
					
					//sleep(1000);
				} 
			catch (InterruptedException e) {
					ioio_.disconnect();
				} catch (ConnectionLostException e) {
					
					throw e;
				}
			}
				
				
				
				
			
		};
	}
}
