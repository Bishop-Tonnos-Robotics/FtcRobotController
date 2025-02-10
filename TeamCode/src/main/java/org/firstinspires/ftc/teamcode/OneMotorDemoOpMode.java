package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Demo: One Motor Control", group="Linear OpMode")
public class OneMotorDemoOpMode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor demoMotor = null; // Only one motor

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the single motor (update "demo_motor" to match the actual name in the Robot Configuration)
        demoMotor = hardwareMap.get(DcMotor.class, "demo_motor");

        // Set the motor direction (change to REVERSE if needed)
        demoMotor.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the driver to press the start button
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            // Control the motor with the left joystick (Y-axis)
            double power = -gamepad1.left_stick_y; // Inverted to match joystick convention
            power = Range.clip(power, -1.0, 1.0); // Ensure power stays within limits

            // Apply power to the motor
            demoMotor.setPower(power);

            // Display telemetry for debugging
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motor Power", "%.2f", power);
            telemetry.update();
        }
    }
}
