const { exec } = require('child_process');
const path = require('path');
const fs = require('fs');

// Set up the database parameters
const dbName = 'bpc-bds-project-assingment-1';
const dbUser = 'JohnDoe';
const backupPath = 'C:/backups';  // Or your desired backup directory

// Get the current date and time for the timestamp
const timestamp = new Date().toISOString().replace(/T/, '_').replace(/\..+/, '').replace(/:/g, '-');
const backupFile = path.join(backupPath, `${dbName}_backup_${timestamp}.sql`);

// Create the backup directory if it doesn't exist
if (!fs.existsSync(backupPath)) {
    fs.mkdirSync(backupPath, { recursive: true });
}

// Build the pg_dump command
const command = `pg_dump -U ${dbUser} -F c -f "${backupFile}" ${dbName}`;

// Execute the backup command
exec(command, (error, stdout, stderr) => {
    if (error) {
        console.error(`Error during backup: ${error.message}`);
        return;
    }
    if (stderr) {
        console.error(`stderr: ${stderr}`);
        return;
    }
    console.log(`Backup completed successfully: ${backupFile}`);
});

//TODO pouprav at sedi na nase