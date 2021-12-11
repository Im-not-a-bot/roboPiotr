package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name = "autonomous red", group = "Autonomous")
public class autonomous2 extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    RobotHardware r;
    @Override
    public void runOpMode() {
        waitForStart();

        r=new RobotHardware(hardwareMap);
        r.encoderDrive(0,-55,0,1,r.CM);
        r.encoderDrive(60,0,0,1,r.CM);

        r.arm.setTargetPosition(360);
        r.arm.setPower(1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        r.armEnd.setPosition(75);

        r.encoderDrive(0,0,180,1,r.CM);

        r.encoderDrive(0,-55,0,1,r.CM);
        r.encoderDrive(-110,0,0,1,r.CM);

        r.carousel.setPower(1);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        r.carousel.setPower(0);

        r.encoderDrive(-15,55,0,1,r.CM);

    }
}