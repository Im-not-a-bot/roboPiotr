package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Crooked Wheels", group="Autonomous")
public class CrookedWheels extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;

    @Override
    public void runOpMode() {

        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();


        // runs after driver presses play
        while (opModeIsActive()) {
            double vertical = -gamepad1.left_stick_y;
            double horizontal = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;


            fl.setPower(Range.clip(vertical + horizontal + turn, -1, 1));
            fr.setPower(Range.clip(vertical - horizontal - turn, -1, 1));
            bl.setPower(Range.clip(vertical - horizontal + turn, -1, 1));
            br.setPower(Range.clip(vertical + horizontal - turn, -1, 1));

        }
    }
}
