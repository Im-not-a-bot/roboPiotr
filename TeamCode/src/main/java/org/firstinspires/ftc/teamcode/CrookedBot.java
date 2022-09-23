package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class CrookedBot {

    //final public static double ONE_CENTIMETER =  630;
    //final public static double ONE_DEGREE = 360;

    public double ONE_CENTIMETER = 47.61;
    public double ONE_DEGREE = 100;

    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;

    public DcMotor carL;
    public DcMotor carR;

    public DcMotor chain;

    public CrookedBot(HardwareMap hardwareMap) {
        FR  = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        chain = hardwareMap.get(DcMotor.class,"chain");
        carL = hardwareMap.get(DcMotor.class,"carL");
        carR = hardwareMap.get(DcMotor.class,"carR");


        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        carL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        carR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);
        chain.setZeroPowerBehavior(BRAKE);
        carL.setZeroPowerBehavior(BRAKE);
        carR.setZeroPowerBehavior(BRAKE);

        //makes accuracy remotely possible
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        chain.setDirection(DcMotorSimple.Direction.FORWARD);
        carL.setDirection(DcMotorSimple.Direction.REVERSE);
        carR.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void encoderDrive(double vertical, double horizontal, double turn, double power) {
        int frontLeftPosition = (int) (FL.getCurrentPosition() + ONE_CENTIMETER * vertical + ONE_CENTIMETER * horizontal + ONE_DEGREE * turn);
        int frontRightPosition = (int) (FR.getCurrentPosition() + ONE_CENTIMETER * vertical - ONE_CENTIMETER * horizontal - ONE_DEGREE * turn);
        int backLeftPosition = (int) (BL.getCurrentPosition() + ONE_CENTIMETER * vertical - ONE_CENTIMETER * horizontal + ONE_DEGREE * turn);
        int backRightPosition = (int) (BR.getCurrentPosition() + ONE_CENTIMETER * vertical + ONE_CENTIMETER * horizontal - ONE_DEGREE * turn);

        FL.setTargetPosition(frontLeftPosition);
        FR.setTargetPosition(frontRightPosition);
        BL.setTargetPosition(backLeftPosition);
        BR.setTargetPosition(backRightPosition);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

    }

    // encoderDrive, but shitty^TM
    public void strafe(int cm, double rd, double pow) {
        rd %= (2 * Math.PI);
        double x = Math.cos(rd); // trig functions take radians
        double y = Math.sin(rd);

        double frontLeftPower = Range.clip(y + x, -1 ,1); // don't think we need turn if we just need to strafe
        double frontRightPower = Range.clip(y - x, -1, 1);
        double backLeftPower = Range.clip(y - x, -1, 1);
        double backRightPower = Range.clip(y + x, -1, 1);

        if (Math.abs(frontLeftPower) > 1 || Math.abs(backLeftPower) > 1 || Math.abs(frontRightPower) > 1 || Math.abs(backRightPower) > 1 ) {
            // Find the largest power
            double max;
            max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
            max = Math.max(Math.abs(frontRightPower), max);
            max = Math.max(Math.abs(backRightPower), max);

            // Divide everything by max (it's positive so we don't need to worry
            // about signs)
            frontLeftPower *= pow/max; // If you don't wanna go vrooom vroom
            backLeftPower *= pow/max;
            frontRightPower *= pow/max;
            backRightPower *= pow/max;
        }

        rd = (rd <= Math.PI) ? rd : rd - Math.PI; // this is because I'm bad at math and equation below works for 0 <= x <= pi

        double actual_v = (2 * Math.sqrt(2)) / (Math.abs(Math.cos(rd)) + Math.sin(rd)); // See my bs math

        FL.setPower(frontLeftPower);
        FR.setPower(frontRightPower);
        BL.setPower(backLeftPower);
        BR.setPower(backRightPower);

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
        // fast_v / actual_v = constant to multiply time by
        /*
        Forward velocity is 2sqrt(2) * unit_v, basically the y-component of each mech. wheel
        strafing at 45 deg to x-axis is 2*unit_v
        strafing at 0 deg is 2sqrt(2) * unit_v
        strafing at 30 deg to x-axis is 2.689 * unit_v

        ostensibly, the wheels are at a 45 deg angle so that horizontal and vertical movement are the same

         */
    }
}