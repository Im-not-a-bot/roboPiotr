package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

public class RobotHardware {
    // todo: fix these shitty constants. Will this still be a problem during mecanum wheels?

    // pie in the sky: ML with camera
    // coding for mecanum and linear extruder / claw
    // consistent abstractions (turning, moving)
    // anything else?
    final public static double ONE_METER = 400; // guess and check lmao
    final public static double ONE_CENTIMETER =  ONE_METER / 100;
    final public static double FULL_ROBOTATION = 240; // found by deriving the inverse sin of LMAO I'm just kidding guess and check
    final public static double ONE_DEGREE = FULL_ROBOTATION / 360;


    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;

    public DcMotor arm;

    public RobotHardware(HardwareMap hardwareMap) {
        FR  = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        arm = hardwareMap.get(DcMotor.class, "arm");

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        FR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);

        arm.setZeroPowerBehavior(BRAKE);

        //makes accuracy remotely possible
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //makes running to angle possible

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setTargetPosition(arm.getCurrentPosition());
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    // all degrees are 'shitty unit circle': 0 degrees is front facing, then go clockwise
    // I'm going to worry about doing turns later... what do I look like, a math kid? trans. DON'T DO TURNS
    // power + turn, power - turn
    /*
    void move(double distance, double turn, double power) {
        int leftPosition = left.getCurrentPosition() + (int)(distance * ONE_CENTIMETER - turn * ONE_DEGREE);
        int rightPosition = right.getCurrentPosition() + (int)(distance * ONE_CENTIMETER + turn * ONE_DEGREE);


        left.setTargetPosition(leftPosition);
        right.setTargetPosition(rightPosition);

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setPower(power);
        right.setPower(power);

        while(left.isBusy() && right.isBusy()); // I think this is right? curse code duplication!

        left.setPower(0);
        right.setPower(0);

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    void turn(double deg, double power) {
        move(0, deg, power);
    }

     */
}