package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;


@Autonomous(name = "AutoRedDuk", group = "Autonomous")
public class AutoRedDuk extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    RobotHardware robot;



    @Override
    public void runOpMode() {


        robot = new RobotHardware(hardwareMap);



        waitForStart();
        runtime.reset();



        robot.encoderDrive(.2,0,0,1);
        sleep(50);
        robot.encoderDrive(0,0,90,1);
        robot.encoderDrive(.75,0,0,1);
        sleep(50);

        robot.carousel.setPower(.5);
        sleep(3000);
        robot.carousel.setPower(0);
        sleep(500);


//        robot.arm.setTargetPosition(188);
//        robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.arm.setPower(1);
    }
}