package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.IOException;

@Autonomous(name="Server Test", group="Autonomous")
public class ServerTest extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        waitForStart();
        Server s = null;    // makes a server, set to null to future code doesn't yell at me

        boolean tf1=true;   // boolean meaning "task failed 1"
        int tp1=8081;       // integer meaning "temporary port 1"

        while(tf1){         // loop to get next available port
            try {
                telemetry.addLine("attempting to start server on port "+tp1);
                s = new Server(tp1++, telemetry);
                tf1=false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        s.start();          // starts server or something
        telemetry.addLine("started server on port "+tp1);

        telemetry.update();

        s.data = "Hello World".getBytes();

        sleep(30000);

    }
}
