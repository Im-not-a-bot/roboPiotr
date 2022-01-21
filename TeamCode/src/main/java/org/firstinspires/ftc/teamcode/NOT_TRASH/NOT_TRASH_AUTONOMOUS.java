package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.NOT_TRASH.RobotHardware;

@TeleOp(name="NOT TRASH AUTONOMOUS", group="Autonomous")
public class NOT_TRASH_AUTONOMOUS extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();


    RobotHardware robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new RobotHardware(hardwareMap);


        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();

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

            if (gamepad1.a) robot.encoderDrive(100, 0, 0, 30);
            if (gamepad1.right_trigger > 0) robot.ONE_CENTIMETER += 10; else if (gamepad1.right_bumper) robot.ONE_CENTIMETER -= 10;
            if (gamepad1.left_trigger > 0) robot.ONE_DEGREE += 10; else if (gamepad1.left_bumper) robot.ONE_DEGREE -= 10;

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