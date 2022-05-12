package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Parking", group = "Autonomous")
public class Parking extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    CrookedBot robot;



    @Override
    public void runOpMode() {
        robot = new CrookedBot(hardwareMap);

        waitForStart();
        runtime.reset();

        robot.encoderDrive(-70,0, 0,0.3);
    }
}