package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name = "Distance Testing", group = "Autonomous")
public class distanceTesting extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    RobotHardware robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new RobotHardware(hardwareMap);

        waitForStart();
        runtime.reset();

        // this can stay, but has to be changed for mecanum (i.e. new move method uses deg and magnitude)
        // autonomous start
        telemetry.addData("Mode", "ENCODER DRIVE");
        telemetry.update();
        robot.encoderDrive(0, 100, 0, 0.1); // 1 meter
        robot.encoderDrive(10, 10, 360, 0.1); // turn
        try{
           Thread.sleep(3000);
       } catch (Exception e){
           e.printStackTrace();
       }
        telemetry.addData("Mode", "STRAFE");
        telemetry.update();
        robot.strafe(100, -Math.PI / 2, 0.1);
        //telemetry.addData("Traveled 1m forwar
        //telemetry.addData("Turned 360deg in place");


        //telemetry.addData("Finished");
        telemetry.update();
    }
}