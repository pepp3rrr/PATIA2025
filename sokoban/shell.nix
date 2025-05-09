{pkgs ? import <nixpkgs> {}}:
with pkgs;
  mkShell {
    packages = with pkgs; [
      jdk21
      maven
    ];
    buildInputs = [
    ];
  }
