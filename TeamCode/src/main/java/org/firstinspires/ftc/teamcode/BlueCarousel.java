package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Blue Carousel", group = "Autonomous")
public class BlueCarousel extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    CrookedBot robot;

    @Override
    public void runOpMode() {
        robot = new CrookedBot(hardwareMap);

        waitForStart();
        runtime.reset();

        // ONE_DEGREE is too small?
        // add more turn? check orientation of 'unit circle'
        // horizontal doesn't seem to work
        robot.encoderDrive(80,-10, 0,0.3);
        robot.carR.setPower(-1);
        robot.encoderDrive(20, 0, 0, 0.02);
        robot.carR.setPower(0);

        robot.encoderDrive(0, -60, 0, 0.3);
    }
}