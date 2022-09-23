package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "autoboiR", group = "Autonomous")
public class autoboiR extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    CrookedBot robot;



    @Override
    public void runOpMode() {


        robot = new CrookedBot(hardwareMap);



        waitForStart();
        runtime.reset();

        robot.encoderDrive(1,0,0,1);
    }
}