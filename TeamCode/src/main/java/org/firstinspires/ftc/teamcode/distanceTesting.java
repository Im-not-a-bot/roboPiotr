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

        robot = new RobotHardware();

        waitForStart();
        runtime.reset();

        // autonomous start
        robot.move(100, 0, 1); // 1 meter
        telemetry.addData("Traveled 1m forward");

        robot.turn(360);
        telemetry.addData("Turned 360deg in place");


        telemetry.addData("Finished");
        telemetry.update();
    }
}