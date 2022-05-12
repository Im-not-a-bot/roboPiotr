package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Red Carousel", group = "Autonomous")
public class RedCarousel extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    CrookedBot robot;



    @Override
    public void runOpMode() {


        robot = new CrookedBot(hardwareMap);

        waitForStart();
        runtime.reset();

        // blue = right
        robot.encoderDrive(80,0, 0,0.3);
        robot.carL.setPower(-1);
        robot.encoderDrive(15, 0, 0, 0.02);
        robot.carL.setPower(0);

        robot.encoderDrive(0, 60, 0, 0.3);
    }
}