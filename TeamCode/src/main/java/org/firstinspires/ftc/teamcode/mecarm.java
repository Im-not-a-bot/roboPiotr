package org.firstinspires.ftc.teamcode;

import android.graphics.Point;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
@TeleOp(name="mecarm (testing)", group="Linear Opmode")
public class
mecarm extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    // REMEMBER!! the robot is SIDEWAYS. The front is what would normally be right
    // naming is based on new orientation!

    RobotHardware robot;
    //
//    public DcMotor R;
//    public DcMotor L;
//
//    public Servo RS;
//    public Servo LS;
//
//    public Point p;
//
//    //for arm calcs
//    public long pt=0;
//    public double h=0;
//
//    public double ang1;
//    public double ang2;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new RobotHardware(hardwareMap);
        //
//        R = hardwareMap.get(DcMotor.class, "R");
//        L = hardwareMap.get(DcMotor.class, "L");
//
//        RS = hardwareMap.get(Servo.class, "RS");
//        LS = hardwareMap.get(Servo.class, "LS");
//        //servo.setPosition(0);
//
//        p=new Point(0,0);

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();

        double centi = 0;
        double deggy = 0;

        // runs after driver presses play
        while (opModeIsActive()) {
                motors();
                if(gamepad1.x){
                    robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
                    robot.arm.setPower(.01);
                } else {
                    robot.arm.setPower(0);
                }

            if (gamepad1.dpad_right) robot.arm.setTargetPosition(robot.arm.getTargetPosition()+1);
            if (gamepad1.dpad_left) robot.arm.setTargetPosition(robot.arm.getTargetPosition()-1);

            // this is to test autonomous functions
            if (gamepad1.a) robot.encoderDrive(100, 0, 0, 30, centi, deggy);
            if (gamepad1.right_trigger) centi += 10; else if (gamepad1.right_bumper) centi -= 10;
            if (gamepad1.left_trigger) deggy += 10; else if (gamepad1.left_bumper) deggy -= 10;



            telemetry.addLine(//"UT (ms)"+  ((double)(System.nanoTime()-pt)/1000000)+"\n"+
                                 "motor position:" + "\n"+
                                 "FR: "+robot.FR.getCurrentPosition()+"\n" +
                                 "FL: "+robot.FL.getCurrentPosition()+"\n" +
                                 "BR: "+robot.BR.getCurrentPosition()+"\n" +
                                 "BL: "+robot.BL.getCurrentPosition()+"\n" +
                                 "\n"
//                                 "arm position:" +"\n"+
//                                 "Target point "+p+"\n"+
//                                 "R "+R.getCurrentPosition()+"\n"+
//                                 "L "+L.getCurrentPosition()+"\n"+
//                                 "RS "+RS.getPosition()+"\n"+
//                                 //"LS "+RS.getPosition()
            );
            //pt=System.nanoTime();
            telemetry.update();
        }
    }

    void motors() {
        //complex trial-and-error stuff
        robot.FR.setPower(Range.clip(-gamepad1.left_stick_y-gamepad1.left_stick_x-.75*gamepad1.right_stick_x,-1,1));
        robot.FL.setPower(Range.clip(-gamepad1.left_stick_y+gamepad1.left_stick_x+.75*gamepad1.right_stick_x,-1,1));
        robot.BR.setPower(Range.clip(-gamepad1.left_stick_y+gamepad1.left_stick_x-.75*gamepad1.right_stick_x,-1,1));
        robot.BL.setPower(Range.clip(-gamepad1.left_stick_y-gamepad1.left_stick_x+.75*gamepad1.right_stick_x,-1,1));
    }
}
