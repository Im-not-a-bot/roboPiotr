package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.NOT_TRASH.RobotHardware;

@TeleOp(name="NOT TRASH - DRIVER", group="Linear Op")
public class NOT_TRASH_DRIVEROP extends LinearOpMode {

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

            if(gamepad1.x){robot.Shoulder.setTargetPosition(robot.Shoulder.getTargetPosition()+1);}
            if(gamepad1.b){robot.Shoulder.setTargetPosition(robot.Shoulder.getTargetPosition()-1);}
            robot.Shoulder.setPower(1);

            if(gamepad1.left_bumper){robot.carousel.setPower(-1);}else
            if(gamepad1.right_bumper){robot.carousel.setPower(1);}else
            {robot.carousel.setPower(0);}

            telemetry.addLine(//"UT (ms)"+  ((double)(System.nanoTime()-pt)/1000000)+"\n"+
                    "motor position:" + "\n"+
                            "FR: "+robot.FR.getCurrentPosition()+"\n" +
                            "FL: "+robot.FL.getCurrentPosition()+"\n" +
                            "BR: "+robot.BR.getCurrentPosition()+"\n" +
                            "BL: "+robot.BL.getCurrentPosition()+"\n"+
                            "sh: "+robot.Shoulder.getCurrentPosition()+"\n"+
                            "st: "+robot.Shoulder.getTargetPosition()+"\n"+
                            "wr: "+robot.Wrist.getCurrentPosition()
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