package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware() {
    final public static double ONE_METER = 4500; // guess and check lmao
	final public static double ONE_CENTIMETER =  ONE_METER / 100;
	final public static double FULL_ROBOTATION = 8000;
	final public static double ONE_DEGREE = FULL_ROBOTATION / 360;


    DcMotor left;
    DcMotor right;
    DcMotor tray1;
    DcMotor tray2;
    CRServo carousel;

    RobotHardware(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        tray1 = hardwareMap.get(DcMotor.class, "tray1");
        tray2 = hardwareMap.get(DcMotor.class, "tray2");
        carousel = hardwareMap.get(CRServo.class, "carousel");

        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.FORWARD);
        // peat didn't set these
        tray1.setDirection(DcMotor.Direction.FORWARD);
        tray2.setDirection(DcMotor.Direction.FORWARD);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        tray1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        tray2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // all degrees are 'shitty unit circle': 0 degrees is front facing, then go clockwise
    // I'm going to worry about doing turns later... what do I look like, a math kid?
    // power + turn, power - turn
    move(double distance, double turn, double power) {
        int leftPosition = robot.left.getCurrentPosition() + (int)(distance * ONE_CENTIMETER + turn * ONE_DEGREE);
        int rightPosition = robot.right.getCurrentPosition() + (int)(distance * ONE_CENTIMETER - turn * ONE_DEGREE);


        robot.left.setTargetPosition(leftPosition);
        robot.right.setTargetPosition(rightPosition);

        robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.left.setPower(power);
        robot.right.setPower(power);

        while(robot.left.isBusy() && robot.right.isBusy()); // I think this is right? curse code duplication!

        robot.left.setPower(0);
        robot.right.setPower(0);

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER;
    }

    turn(double deg, double power) {
        move(0, deg, power);
    }
}