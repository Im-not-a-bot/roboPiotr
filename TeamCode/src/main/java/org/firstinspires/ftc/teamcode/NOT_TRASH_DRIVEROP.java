package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.RobotHardware;

@TeleOp(name="NOT TRASH (Tokyo edit)", group="Autonomous")
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

        int arm_counter = 100; // max: 200, min: 0

        // runs after driver presses play
        while (opModeIsActive()) {
            double vertical = -gamepad1.left_stick_y;
            double horizontal = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            if (gamepad1.left_bumper) robot.carousel.setPower(1);
            else if (gamepad1.right_bumper) robot.carousel.setPower(-1);
            else robot.carousel.setPower(0);

            if (gamepad1.left_trigger > 0.5) robot.chain.setPower(0.1);
            else if (gamepad1.right_trigger > 0.5) robot.chain.setPower(-0.1);
            else robot.chain.setPower(0);


            armControl: {
                if (gamepad1.dpad_up) {
                    arm_counter += 2;
                } else if (gamepad1.dpad_down) {
                    arm_counter -= 2;
                } else
                    break armControl;

                robot.arm.setTargetPosition(arm_counter);
                ((DcMotorEx)(robot.arm)).setVelocity(2000);
            }


            //if (gamepad1.b)
            //    robot.encoderDrive(100, 100, 0, 0.25);
            //if (gamepad1.dpad_left) robot.ONE_CENTIMETER += 5;
            //else if (gamepad1.dpad_right) robot.ONE_CENTIMETER -= 5;

            robot.FL.setPower(Range.clip(vertical + horizontal + turn, -1, 1));
            robot.FR.setPower(Range.clip(vertical - horizontal - turn, -1, 1));
            robot.BL.setPower(Range.clip(vertical - horizontal + turn, -1, 1));
            robot.BR.setPower(Range.clip(vertical + horizontal - turn, -1, 1));

            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", robot.FL.getPower(), robot.FR.getPower(), robot.BL.getPower(), robot.BR.getPower());
            telemetry.addData("Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", robot.FL.getCurrentPosition(), robot.FR.getCurrentPosition(), robot.BL.getCurrentPosition(), robot.BR.getCurrentPosition());
            telemetry.addData("More Motors", "Arm (%.2f), End (%.2f), Carousel (%.2f)", robot.arm.getPower(), robot.chain.getPower(), robot.carousel.getPower());
            telemetry.addData("More Encoders", "Arm (%d), End (%d), Carousel (%d)", robot.arm.getCurrentPosition(), robot.chain.getCurrentPosition(), robot.carousel.getCurrentPosition());
            telemetry.addData("Arm Counter: %d", arm_counter);
            // this is getting refactored to autonomous mode instead
            //telemetry.addData("By King's Decree, 1cm = %d", robot.ONE_CENTIMETER);
            if (robot.arm.isBusy()) telemetry.addData("Doop doop doop %s", "doop");

            telemetry.update();
        }
    }
}
