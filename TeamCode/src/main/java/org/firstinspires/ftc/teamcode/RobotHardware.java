package org.firstinspires.ftc.teamcode.NOT_TRASH;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class RobotHardware {

    // pie in the sky: ML with camera
    // ^^ ok george, but i might get my lazy ass to work over break

    // coding for mecanum and linear extruder / claw
    // consistent abstractions (turning, moving)
    // anything else?

    // ^^ yes. there is a definite lack of shitty variable names such as "beans"

    //final public static double ONE_CENTIMETER =  630;
    //final public static double ONE_DEGREE = 360;

    public double ONE_CENTIMETER = 100;
    public double ONE_DEGREE = 100;

    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;

    public DcMotor Shoulder;
    public DcMotor Wrist;
    public DcMotor carousel;

    public RobotHardware(HardwareMap hardwareMap) {
        FR  = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        Shoulder = hardwareMap.get(DcMotor.class, "shoulder");
        Wrist = hardwareMap.get(DcMotor.class,"wrist");
        carousel = hardwareMap.get(DcMotor.class,"carousel");

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);
        Shoulder.setZeroPowerBehavior(BRAKE);
        Wrist.setZeroPowerBehavior(BRAKE);
        carousel.setZeroPowerBehavior(BRAKE);

        Shoulder.setTargetPosition(0);
        Wrist.setTargetPosition(0);

        //makes accuracy remotely possible
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        carousel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        Shoulder.setDirection(DcMotorSimple.Direction.FORWARD);
        Wrist.setDirection(DcMotorSimple.Direction.FORWARD);
        carousel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void encoderDrive(double distanceX, double distanceY, double turn, double power) {
        double vertical = -distanceX;
        double horizontal = -distanceY;

        int frontLeftPosition = (int) (FL.getCurrentPosition() + ONE_CENTIMETER * vertical - ONE_CENTIMETER * horizontal + ONE_DEGREE * turn);
        int frontRightPosition = (int) (FR.getCurrentPosition() + ONE_CENTIMETER * vertical + ONE_CENTIMETER * horizontal - ONE_DEGREE * turn);
        int backLeftPosition = (int) (BL.getCurrentPosition() + ONE_CENTIMETER * vertical + ONE_CENTIMETER * horizontal + ONE_DEGREE * turn);
        int backRightPosition = (int) (BR.getCurrentPosition() + ONE_CENTIMETER * vertical - ONE_CENTIMETER * horizontal - ONE_DEGREE * turn);

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

        while (FL.isBusy() && FR.isBusy() && BL.isBusy() && BR.isBusy()) {
            /*
            telemetry.addData("Target Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", frontLeftPosition, frontRightPosition, backLeftPosition, backRightPosition);
            telemetry.addData("Current Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", FL.getCurrentPosition(), FR.getCurrentPosition(), BL.getCurrentPosition(), BR.getCurrentPosition());
            telemetry.update();
            ssshhhhhh */
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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