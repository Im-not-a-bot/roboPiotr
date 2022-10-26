package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
public class RobotHardware {
    // todo: fix these shitty constants. Will this still be a problem during mecanum wheels?
    // i sure hope not! -Peat

    // pie in the sky: ML with camera
    // ^^ ok george, but i might get my lazy ass to work over break

    // coding for mecanum and linear extruder / claw
    // consistent abstractions (turning, moving)
    // anything else?

    // ^^ yes. there is a definite lack of shitty variable names such as "beans"

    final public static double ONE_METER = 6300; // guess and check lmao
    final public static double ONE_CENTIMETER =  630;

    final public static double CM =1;
    final public static double M=100;
    final public static double FULL_ROBOTATION = 240;
    final public static double ONE_DEGREE = FULL_ROBOTATION / 360;


    final static double MAX_VELOCITY = (ONE_CENTIMETER * 12) / (2 * Math.sqrt(2));

    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;

    public ColorSensor color_sensor;
    public RobotHardware(HardwareMap hardwareMap) {
        FR  = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        color_sensor = hardwareMap.colorSensor.get("color");
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);

        //makes accuracy remotely possible
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    // moved away from constants for testing purposes
    public void encoderDrive(double distanceX, double distanceY, double turn, double power, double one_centimeter, double one_degree) {
        double vertical = -distanceX;
        double horizontal = -distanceY;

        int frontLeftPosition = (int) (FL.getCurrentPosition() + one_centimeter * vertical - one_centimeter * horizontal + one_degree * turn);
        int frontRightPosition = (int) (FR.getCurrentPosition() + one_centimeter * vertical + one_centimeter * horizontal - one_degree * turn);
        int backLeftPosition = (int) (BL.getCurrentPosition() + one_centimeter * vertical + one_centimeter * horizontal + one_degree * turn);
        int backRightPosition = (int) (BR.getCurrentPosition() + one_centimeter * vertical - one_centimeter * horizontal - one_degree * turn);

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

        long time = (long)(cm / (MAX_VELOCITY * actual_v));

        FL.setPower(frontLeftPower);
        FR.setPower(frontRightPower);
        BL.setPower(backLeftPower);
        BR.setPower(backRightPower);

       try{
           Thread.sleep(time);
       } catch (Exception e){
           e.printStackTrace();
       }

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
    /*
    void turn(double deg, double power) {
        move(0, deg, power);
    }

     */
}